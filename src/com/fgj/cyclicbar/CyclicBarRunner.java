package com.fgj.cyclicbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarRunner {
	/**
	 * 模拟CyclicBarrier并行处理二维数组，每一个线程处理一行数据
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game(4, 8);
		//多人平均要比单人快n倍，n就是线程数量
//		new CyclicBarRunner().singleRun(game);
		new CyclicBarRunner().multiRun(game);
	}
	
	public void multiRun(Game game) {
		long start = System.currentTimeMillis();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(game.getRooms(), new BarrierAction(game));
		//每一个task相当于一个玩家
		List<CyclicBarTask> tasks = new ArrayList<>();
		//线程驱动任务
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < game.getRooms(); i++) {
			CyclicBarTask task = new CyclicBarTask(game, cyclicBarrier);
			tasks.add(task);
			threads.add(new Thread(task, i + 1 + "号"));
		}
		System.out.println("游戏开始 - - - - - - - - - - ");
		//obtainBall的size和房间数一样，这样每一个玩家就能分配到一个房间
		game.gameStart(threads, tasks, new ObtainBall(game.getRooms()));
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("多人总耗时：" + (System.currentTimeMillis() - start));
	}
	
	public void singleRun(Game game) {
		long start = System.currentTimeMillis();
		int[][] region = game.getRegion();
		for (int i = 0; i < region.length; i++) {
			for (int j = 0; j < region[i].length; j++) {
				try {
					Thread.sleep(region[i][j] * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("单人总耗时：" + (System.currentTimeMillis() - start));
	}
}
