package com.net.pic;

import java.util.concurrent.CyclicBarrier;

public class CycleBarrierDemo {
	public static void main(String[] args) {
    CyclicBarrier cb = new CyclicBarrier(10); 
    new Downloader("d1", "http://img2.3lian.com/img2007/17/08/001.jpg", "001.jpg", cb).start();
    new Downloader("d2", "http://img2.3lian.com/img2007/17/08/002.jpg", "002.jpg", cb).start();
    new Downloader("d3", "http://img2.3lian.com/img2007/17/08/003.jpg", "003.jpg", cb).start();
    new Downloader("d4", "http://img2.3lian.com/img2007/17/08/004.jpg", "004.jpg", cb).start();
    new Downloader("d5", "http://img2.3lian.com/img2007/17/08/005.jpg", "005.jpg", cb).start();
    new Downloader("d6", "http://img2.3lian.com/img2007/17/08/006.jpg", "006.jpg", cb).start();
    new Downloader("d7", "http://img2.3lian.com/img2007/17/08/007.jpg", "007.jpg", cb).start();
    new Downloader("d8", "http://img2.3lian.com/img2007/17/08/008.jpg", "008.jpg", cb).start();
    new Downloader("d9", "http://img2.3lian.com/img2007/17/05/0849m.jpg", "0849m.jpg", cb).start();
    new Downloader("d10", "http://img2.3lian.com/img2007/17/05/08943m.jpg", "0849m.jpg", cb).start();
}

}
