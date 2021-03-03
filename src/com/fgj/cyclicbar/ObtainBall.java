package com.fgj.cyclicbar;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 模拟从球箱中抓球
 * balls代表球箱，里面的每一个元素代表一个球
 * copy代表球箱的副本，用于重置球箱
 */
public class ObtainBall {
	private final Random random = new Random();
	private List<Integer> balls;
	private List<Integer> copy;
	
	public ObtainBall(int[] balls) {
		this.balls = new LinkedList<>();
		for (int ball : balls) {
			this.balls.add(ball);
		}
		this.copy = new LinkedList<>(this.balls);
	}
	
	public ObtainBall(int size) {
		this.balls = new LinkedList<>();
		for (int i = 1; i <= size; i++) {
			this.balls.add(i);
		}
		this.copy = new LinkedList<>(this.balls);
	}
	
	/**
	 * 随机获取球箱中的一个球
	 * @return
	 */
	public Integer obtain() {
		if (balls.isEmpty()) {
			reset();
		}
		//随机出一个索引，记录下这个索引索引对应的元素值，然后移除掉这个元素
		int tarIndex = random.nextInt(balls.size());
		int res = balls.get(tarIndex);
		balls.remove(tarIndex);
		return res;
	}
	
	/**
	 * 重置球箱
	 */
	private void reset() {
		this.balls = this.copy;
		this.copy = new LinkedList<>(this.balls);
	}
}
