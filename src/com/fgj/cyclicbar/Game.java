package com.fgj.cyclicbar;

import java.util.List;
import java.util.Random;

/**
 * 模拟多人刷副本，region表示副本中的地图
 * region的每一行代表关卡，每个关卡都有多个房间
 * region的每一列代表关卡里的房间
 * 每进入一个关卡的时候，不同的玩家分别处理这个关卡中不同的房间（房间随机分配），等所有玩家都打完怪之后才能进入到下一个关卡，打的快的人打完怪之后，
 * 必须等待其他人打完怪
 */
public class Game {
	/**
	 * 地图
	 */
	private int[][] region;
	/**
	 * 每个关卡的房间数
	 */
	private int rooms;
	/**
	 * 关卡总数
	 */
	private int levels;
	/**
	 * 当前关卡
	 */
	private int curLevel;
	/**
	 * 游戏是否结束
	 */
	private boolean over;
	/**
	 * 所有玩家
	 */
	private List<CyclicBarTask> tasks;
	private List<Thread> threads;
	/**
	 * 用于给每个玩家随机分配房间
	 */
	private ObtainBall obtainBall;
	
	public Game(int levels, int rooms) {
		this.rooms = rooms;
		this.levels = levels;
		//构造游戏地图数据
		Random random = new Random();
		region = new int[levels][rooms];
		for (int i = 0; i < region.length; i++) {
			for (int j = 0; j < region[i].length; j++) {
				//每个数组元素的值代表房间内怪物数量，范围是：[1,10]
//				region[i][j] = 1;
				region[i][j] = random.nextInt(10) + 1;
			}
		}
	}
	
	public void gameStart(List<Thread> threads, List<CyclicBarTask> tasks, ObtainBall obtainBall) {
		over = false;
		curLevel = 1;
		this.tasks = tasks;
		this.obtainBall = obtainBall;
		this.threads = threads;
		allocateRoom();
		for (Thread thread : threads) {
			thread.start();
		}
	}
	
	/**
	 * 游戏结束
	 */
	private void gameOver() {
		over = true;
		curLevel = 1;
		System.out.println("游戏结束！！！");
	}
	
	/**
	 * 为每个玩家分配房间
	 */
	private void allocateRoom() {
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setRoom(obtainBall.obtain());
		}
	}
	
	/**
	 * 下一关
	 *
	 * @return
	 */
	public boolean nextLevel() {
		if (over) {
			System.out.println("游戏已经结束...");
			return false;
		}
		if (curLevel == levels) {
			gameOver();
			return false;
		}
		System.out.println("第" + curLevel + "关结束，正在加载下一关...");
		curLevel++;
		System.out.println("第" + curLevel + "关开始，let's go!");
		allocateRoom();
		return true;
	}
	
	public boolean isOver() {
		return over;
	}
	
	public int[][] getRegion() {
		return region;
	}
	
	public int getRooms() {
		return rooms;
	}
	
	public int getLevels() {
		return levels;
	}
	
	public int getCurLevel() {
		return curLevel;
	}
}
