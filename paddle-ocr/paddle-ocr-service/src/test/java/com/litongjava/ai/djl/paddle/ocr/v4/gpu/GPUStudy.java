package com.litongjava.ai.djl.paddle.ocr.v4.gpu;

import org.junit.Test;

import ai.djl.Device;
import ai.djl.engine.Engine;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;

public class GPUStudy {
  /* Return the i'th GPU if it exists, otherwise return the CPU */
  public Device tryGpu(int i) {
    return Engine.getInstance().getGpuCount() > i ? Device.gpu(i) : Device.cpu();
  }

  /* Return all available GPUs or the [CPU] if no GPU exists */
  public Device[] tryAllGpus() {
    int gpuCount = Engine.getInstance().getGpuCount();
    if (gpuCount > 0) {
      Device[] devices = new Device[gpuCount];
      for (int i = 0; i < gpuCount; i++) {
        devices[i] = Device.gpu(i);
      }
      return devices;
    }
    return new Device[] { Device.cpu() };
  }

  public static void main(String[] args) {
    System.out.println(Device.cpu());
    System.out.println(Device.gpu());
    System.out.println(Device.gpu(1));

    System.out.println("GPU count: " + Engine.getInstance().getGpuCount());
  }

  @Test
  public void getDevices() {
    NDManager manager = NDManager.newBaseManager();
    NDArray x = manager.create(new int[] { 1, 2, 3 });
    Device device = x.getDevice();
    System.out.println(device);
  }

}
