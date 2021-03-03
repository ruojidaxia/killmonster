package com.fgj.cyclicbar;

public class BarrierAction implements Runnable {
	private Game game;
	
	public BarrierAction(Game game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		//控制游戏关卡进度
		if (!game.isOver()) {
			System.out.println("所有人都打完怪了");
			game.nextLevel();
		}
	}
}
