package com.fgj.cyclicbar;

import java.text.MessageFormat;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarTask implements Runnable {
	private Game game;
	private int[][] region;
	private CyclicBarrier cyclicBarrier;
	/**
	 * 房间数
	 */
	private int room;
	
	public CyclicBarTask(Game game, CyclicBarrier cyclicBarrier) {
		this.game = game;
		this.region = game.getRegion();
		this.cyclicBarrier = cyclicBarrier;
	}
	
	@Override
	public void run() {
		while (!game.isOver()) {
			//根据关卡数和房间数先计算好数组索引
			int row = game.getCurLevel() - 1;
			int col = room - 1;
			try {
				long start = System.currentTimeMillis();
				System.out.println(MessageFormat.format("{0}进入第{2}个房间，房间内怪物总数:{3}，冲鸭...",
					Thread.currentThread().getName(), room, region[row][col]));
				//模拟打怪，怪物数量越多，休眠时间越长
				Thread.sleep(region[row][col] * 100);
				System.out.println(MessageFormat.format("{0}已经打完所有的怪了，耗时：{1}",
					Thread.currentThread().getName(), System.currentTimeMillis() - start));
				//打完怪之后需要等待其他玩家都打完怪
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void setRoom(int room) {
		this.room = room;
	}
}
