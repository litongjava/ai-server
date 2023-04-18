package com.litongjava.ai.server.demo;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.NoBatchifyTranslator;
import ai.djl.translate.TranslatorContext;
import com.litongjava.ai.server.utils.JFramUtils;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author litongjava@qq.com on 2023/4/18 15:47
 */
public class PaddleDetectMaskDemo {
  public static void main(String[] args) throws IOException {
    new PaddleDetectMaskDemo().index();

  }

  @SneakyThrows
  private void index() throws IOException {
    String url = "https://raw.githubusercontent.com/PaddlePaddle/PaddleHub/release/v1.5/demo/mask_detection/python/images/mask.jpg";
    Image img = ImageFactory.getInstance().fromUrl(url);
    //JFramUtils.showBufferedImage("src",img.getWrappedImage());
    // NDList ndArrays = processImageInput(NDManager.newBaseManager(), img, 0.5f);

    Predictor<Image, DetectedObjects> detecter = getImageDetectedObjectsPredictor();
    Predictor<Image, Classifications> classifier = getImageClassificationsPredictor();

    DetectedObjects inferenceResult = detecter.predict(img);
//    Image newImage = img.duplicate();
//    newImage.drawBoundingBoxes(inferenceResult);
//    JFramUtils.showBufferedImage("src", newImage.getWrappedImage());

    List<DetectedObjects.DetectedObject> faces = inferenceResult.items();
//    Image subImage = getSubImage(img, faces.get(2).getBoundingBox());
//    JFramUtils.showBufferedImage("src", subImage.getWrappedImage());



    List<String> names = new ArrayList<>();
    List<Double> prob = new ArrayList<>();
    List<BoundingBox> rect = new ArrayList<>();
    for (DetectedObjects.DetectedObject face : faces) {
      Image subImg = getSubImage(img, face.getBoundingBox());
      Classifications classifications = classifier.predict(subImg);
      names.add(classifications.best().getClassName());
      prob.add(face.getProbability());
      rect.add(face.getBoundingBox());
    }

    Image newImage = img.duplicate();
    newImage.drawBoundingBoxes(new DetectedObjects(names, prob, rect));;
    JFramUtils.showBufferedImage("dst",newImage.getWrappedImage());

//    NDArray tempOutput = NDManager.newBaseManager().create(new float[]{1f, 0.99f, 0.1f, 0.1f, 0.2f, 0.2f}, new Shape(1, 6));
//    DetectedObjects testBox = processImageOutput(new NDList(tempOutput), Arrays.asList("Not Face", "Face"), 0.7f);
//    Image newImage = img.duplicate();
//    newImage.drawBoundingBoxes(testBox);

  }

  private Predictor<Image, DetectedObjects> getImageDetectedObjectsPredictor() throws IOException, ModelNotFoundException, MalformedModelException {
    Criteria<Image, DetectedObjects> criteria = Criteria.builder()
      .setTypes(Image.class, DetectedObjects.class)
      .optModelUrls("djl://ai.djl.paddlepaddle/face_detection/0.0.1/mask_detection")
      .optFilter("flavor", "server")
      .optTranslator(new FaceTranslator(0.5f, 0.7f))
      .build();

    ZooModel<Image, DetectedObjects> model = criteria.loadModel();
    Predictor<Image, DetectedObjects> predictor = model.newPredictor();
    return predictor;
  }

  private static Predictor<Image, Classifications> getImageClassificationsPredictor() throws IOException, ModelNotFoundException, MalformedModelException {
    Criteria.Builder<?, ?> classifyCrieriaBuilder = Criteria.builder();
    Criteria<Image, Classifications> classifyCrieria = classifyCrieriaBuilder
      .setTypes(Image.class, Classifications.class)
      .optModelUrls("djl://ai.djl.paddlepaddle/mask_classification/0.0.1/mask_classification")
      .optFilter("flavor", "server")
      .optTranslator(
        ImageClassificationTranslator.builder()
          .addTransform(new Resize(128, 128))
          .addTransform(new ToTensor()) // HWC -> CHW div(255)
          .addTransform(
            new Normalize(
              new float[]{0.5f, 0.5f, 0.5f},
              new float[]{1.0f, 1.0f, 1.0f}))
          .addTransform(nd -> nd.flip(0)) // RGB -> GBR
          .build())
      .build();

    ZooModel<Image, Classifications> classifyModel = classifyCrieria.loadModel();
    Predictor<Image, Classifications> classifier = classifyModel.newPredictor();
    return classifier;
  }

  public NDList processImageInput(NDManager manager, Image input, float shrink) {
    NDArray array = input.toNDArray(manager);
    Shape shape = array.getShape();
    array = NDImageUtils.resize(array, (int) (shape.get(1) * shrink), (int) (shape.get(0) * shrink));
    array = array.transpose(2, 0, 1).flip(0); // HWC -> CHW BGR -> RGB
    NDArray mean = manager.create(new float[]{104f, 117f, 123f}, new Shape(3, 1, 1));
    array = array.sub(mean).mul(0.007843f); // normalization
    array = array.expandDims(0); // make batch dimension
    return new NDList(array);
  }

  DetectedObjects processImageOutput(NDList list, List<String> className, float threshold) {
    NDArray result = list.singletonOrThrow();
    float[] probabilities = result.get(":,1").toFloatArray();
    List<String> names = new ArrayList<>();
    List<Double> prob = new ArrayList<>();
    List<BoundingBox> boxes = new ArrayList<>();
    for (int i = 0; i < probabilities.length; i++) {
      if (probabilities[i] >= threshold) {
        float[] array = result.get(i).toFloatArray();
        names.add(className.get((int) array[0]));
        prob.add((double) probabilities[i]);
        boxes.add(
          new Rectangle(array[2], array[3], array[4] - array[2], array[5] - array[3]));
      }
    }
    return new DetectedObjects(names, prob, boxes);
  }

  int[] extendSquare(
    double xmin, double ymin, double width, double height, double percentage) {
    double centerx = xmin + width / 2;
    double centery = ymin + height / 2;
    double maxDist = Math.max(width / 2, height / 2) * (1 + percentage);
    return new int[]{
      (int) (centerx - maxDist), (int) (centery - maxDist), (int) (2 * maxDist)
    };
  }

  Image getSubImage(Image img, BoundingBox box) {
    Rectangle rect = box.getBounds();
    int width = img.getWidth();
    int height = img.getHeight();
    int[] squareBox =
      extendSquare(
        rect.getX() * width,
        rect.getY() * height,
        rect.getWidth() * width,
        rect.getHeight() * height,
        0.18);
    return img.getSubImage(squareBox[0], squareBox[1], squareBox[2], squareBox[2]);
  }


  class FaceTranslator implements NoBatchifyTranslator<Image, DetectedObjects> {

    private float shrink;
    private float threshold;
    private List<String> className;

    FaceTranslator(float shrink, float threshold) {
      this.shrink = shrink;
      this.threshold = threshold;
      className = Arrays.asList("Not Face", "Face");
    }

    @Override
    public DetectedObjects processOutput(TranslatorContext ctx, NDList list) {
      return processImageOutput(list, className, threshold);
    }

    @Override
    public NDList processInput(TranslatorContext ctx, Image input) {
      return processImageInput(ctx.getNDManager(), input, shrink);
    }
  }


}
