package com.star.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	// 卡片数值
	private Card cardMaps[][] = new Card[4][4];
	// 数值为0的卡片
	private List<Point> emptyPoint = new ArrayList<Point>();
	// 当前GameView的class,实现其他类可以直接调用该类的方法
	private static GameView gameViewCls;
	private GridLayout gameViewLayout;
	
	private boolean isGameStarted = false;
	private boolean isGameSuccess = false;

	public boolean isGameStarted() {
		return isGameStarted;
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		gameViewCls = this;
		initGameView(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		gameViewCls = this;
		initGameView(context);
	}

	public GameView(Context context) {
		super(context);

		gameViewCls = this;
		initGameView(context);
	}

	public static GameView getGameViewCls() {
		return gameViewCls;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		MainActivity.getMainActivity().setScreenOrientation(w,h);
		
		LayoutParams lp = new LayoutParams();
		lp.setMargins(0, Math.abs(w-h) / 2, 0, 0);
		
		gameViewLayout.setLayoutParams(lp);
		System.out.println("GameView----------->onSizeChanged");
		System.out.println("onSizeChanged Width: "+w);
		System.out.println("onSizeChanged Height: "+h);
		int CardWidth = (Math.min(w, h) - 20) / 4;

		addCards(CardWidth, CardWidth);
		startGame();
	}

	/*
	 * 初始化游戏主界面
	 */
	private void initGameView(Context context) {
		System.out.println("GameView----------->initGameView");
		
		// 获取GameViewLayout并设置背景颜色
		gameViewLayout = (GridLayout) findViewById(R.id.gameViewLayout);
		gameViewLayout.setBackgroundColor(0xFFD4C8BD);
		
		// 设置GridView每行只有4个卡片
		setColumnCount(4);
		
		// 设置监听OnTouch事件，判断用户滑动方向
		gameViewLayout.setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;

				case MotionEvent.ACTION_UP:
					isGameStarted = true;
					offsetX = startX - event.getX();
					offsetY = startY - event.getY();

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							//System.out.println("Right");
							swipeRight();
						} else if (offsetX > 5) {
							//System.out.println("Left");
							swipeLeft();
						}
					} else {

						if (offsetY < -5) {
							//System.out.println("Down");
							swipeDown();
						} else if (offsetY > 5) {
							//System.out.println("Up");
							swipeUp();
						}
					}
					break;
				}
				return true;
			}
		});
	}

	/*
	 *  开始游戏 的准备工作
	 *  初始化16张游戏卡片为0  
	 *  并设置数值（2,4）两个随机游戏卡片
	 */
	public void startGame() {
		isGameStarted = false;
		
		// 调用MainActivity的方法清除游戏得分
		MainActivity.getMainActivity().clearScore();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMaps[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}

	/*
	 * 添加随机数
	 */
	private void addRandomNum() {
		emptyPoint.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMaps[x][y].getNum() <= 0)
					emptyPoint.add(new Point(x, y));
			}
		}
		Point point = emptyPoint.remove((int) (Math.random() * emptyPoint
				.size()));
		cardMaps[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	/*
	 * 添加16张游戏卡片
	 */
	private void addCards(int cardWidth, int cardheight) {
		Card c;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(0);
				cardMaps[x][y] = c;
				gameViewLayout.addView(c, cardWidth, cardheight);
			}
		}
	}

	private void swipeLeft() {

		boolean isChange = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMaps[x1][y].getNum() > 0) {
						if (cardMaps[x][y].getNum() <= 0) {
							cardMaps[x][y].setNum(cardMaps[x1][y].getNum());
							cardMaps[x1][y].setNum(0);
							x--;
							isChange = true;
						} else if (cardMaps[x][y].equals(cardMaps[x1][y])) {
							cardMaps[x][y].setNum(cardMaps[x][y].getNum() << 1);
							cardMaps[x1][y].setNum(0);
							isChange = true;
							MainActivity.getMainActivity().addScore(
									cardMaps[x][y].getNum());
						}
						break;
					}
				}
			}
		}

		if (isChange) {
			addRandomNum();
			isGameOver();
		}
	}

	private void swipeRight() {
		boolean isChange = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMaps[x1][y].getNum() > 0) {
						if (cardMaps[x][y].getNum() <= 0) {
							cardMaps[x][y].setNum(cardMaps[x1][y].getNum());
							cardMaps[x1][y].setNum(0);
							x++;
							isChange = true;
						} else if (cardMaps[x][y].equals(cardMaps[x1][y])) {
							cardMaps[x][y].setNum(cardMaps[x][y].getNum() << 1);
							cardMaps[x1][y].setNum(0);
							isChange = true;
							MainActivity.getMainActivity().addScore(
									cardMaps[x][y].getNum());
						}
						break;
					}
				}
			}
		}

		if (isChange) {
			addRandomNum();
			isGameOver();
		}
	}

	private void swipeUp() {
		boolean isChange = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMaps[x][y1].getNum() > 0) {
						if (cardMaps[x][y].getNum() <= 0) {
							cardMaps[x][y].setNum(cardMaps[x][y1].getNum());
							cardMaps[x][y1].setNum(0);
							y--;
							isChange = true;
						} else if (cardMaps[x][y].equals(cardMaps[x][y1])) {
							cardMaps[x][y].setNum(cardMaps[x][y].getNum() << 1);
							cardMaps[x][y1].setNum(0);
							isChange = true;
							MainActivity.getMainActivity().addScore(
									cardMaps[x][y].getNum());
						}
						break;
					}
				}
			}
		}

		if (isChange) {
			addRandomNum();
			isGameOver();
		}
	}

	private void swipeDown() {

		boolean isChange = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMaps[x][y1].getNum() > 0) {
						if (cardMaps[x][y].getNum() <= 0) {
							cardMaps[x][y].setNum(cardMaps[x][y1].getNum());
							cardMaps[x][y1].setNum(0);
							y++;
							isChange = true;
						} else if (cardMaps[x][y].equals(cardMaps[x][y1])) {
							cardMaps[x][y].setNum(cardMaps[x][y].getNum() << 1);
							cardMaps[x][y1].setNum(0);
							isChange = true;
							MainActivity.getMainActivity().addScore(
									cardMaps[x][y].getNum());
						}
						break;
					}
				}
			}
		}

		if (isChange) {
			addRandomNum();
			isGameOver();
		}
	}

	/*
	 * 判断游戏是否结束、胜利
	 */
	private void isGameOver() {

		boolean isGameOver = true;

		ALL: for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMaps[x][y].getNum() <= 0
						|| (x > 0 && cardMaps[x][y].equals(cardMaps[x - 1][y]))
						|| (x < 3 && cardMaps[x][y].equals(cardMaps[x + 1][y]))
						|| (y > 0 && cardMaps[x][y].equals(cardMaps[x][y - 1]))
						|| (y < 3 && cardMaps[x][y].equals(cardMaps[x][y + 1]))) {
					isGameOver = false;
					break ALL;
				}
			}
		}

		if (isGameOver) {

			new AlertDialog.Builder(getContext())
					.setCancelable(false)
					.setTitle("Game Result")
					.setMessage("Game Over!")
					.setNegativeButton("Quit!",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									// System.out.println("------------------Quit---------------");
									MainActivity.getMainActivity().quitGame();
								}
							})
					.setPositiveButton("Try Again.",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									startGame();
								}
							}).show();
		}

		if (isGameSuccess == false) {
			ALL_OK: for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					if (cardMaps[x][y].getNum() >= 2048) {
						isGameSuccess = true;
						break ALL_OK;
					}
				}
			}
			if (isGameSuccess) {

				new AlertDialog.Builder(getContext())
						.setCancelable(false)
						.setTitle("Game Win")
						.setMessage("Congratulate!")
						.setNegativeButton("Quit!",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// System.out.println("------------------Quit---------------");
										MainActivity.getMainActivity()
												.quitGame();
									}
								})
						.setPositiveButton("Go on.",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
									}
								}).show();
			}
		}

	}

}
