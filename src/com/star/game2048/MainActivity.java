package com.star.game2048;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	// private Button startGame;
	private static MainActivity mainActivity = null;
	private int currentScore = 0;
	private int bestScore = 0;
	// private TextView tvCurrentScore;
	// private TextView tvBestScore;
	private CornerTextView tvCurrentScore;
	private CornerTextView tvBestScore;

	public MainActivity() {
		mainActivity = this;
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置为无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置为全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置屏幕方向
		//setScreenOrientation();
		setContentView(R.layout.activity_main);

		// 获取当前分数CornerTextView
		tvCurrentScore = (CornerTextView) findViewById(R.id.tvCurrentScore);
		// tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
		tvCurrentScore.setBackgroundColor(0xFFBBADA0);
		tvCurrentScore.setTextColor(0xFFEEE4DA);
		tvCurrentScore.setInsertStaticString("SCORE");

		// 获取最高分数CornerTextView
		tvBestScore = (CornerTextView) findViewById(R.id.tvBestScore);
		// tvBestScore = (TextView) findViewById(R.id.tvBestScore);
		tvBestScore.setBackgroundColor(0xFFBBADA0);
		tvBestScore.setTextColor(0xFFEEE4DA);
		tvBestScore.setInsertStaticString("BEST");

		// 从SharePreferences获取历史最高分数
		SharedPreferences sharePref = getSharedPreferences("shared_file", 0);
		bestScore = sharePref.getInt("bestScore", 0);
		System.out.println("onCreate----> first bestScore :" + bestScore);
		tvBestScore.setText(bestScore + "");

		/*
		 * startGame = (Button) findViewById(R.id.startGame);
		 * startGame.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * GameView.getGameViewCls().startGame(); } });
		 */
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setScreenOrientation(int width, int height){
		
		if(width > height){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			// 默认设置为竖屏模式
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
		
//		// 定义DisplayMetrics对象
//		DisplayMetrics dm = new DisplayMetrics();
//		
//		// 取得窗口属性
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int width = dm.widthPixels;
//		int height = dm.heightPixels;
//		System.out.println("Screen Width: " + width);
//		System.out.println("Screen Height: " + height);
//		if(width > height){
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}else{
//			// 默认设置为竖屏模式
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		}
		
		
	}
	
	/*
	 * 退出游戏
	 */
	public void quitGame() {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& GameView.getGameViewCls().isGameStarted()) {
			new AlertDialog.Builder(this)
					.setTitle("Exit Game")
					.setMessage("Are You Sure?")
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
								}
							})
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									quitGame();
								}
							}).show();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onPause() {
		// 游戏将要完全退出时，先保存用户最高数
		SharedPreferences sharePref = getSharedPreferences("shared_file", 0);
		int tmpBestScore = sharePref.getInt("bestScore", 0);
		// System.out
		// .println("onKeyDown----> secode bestScore :"
		// + bestScore
		// + "	currentScore :"
		// + currentScore
		// + "	tmpBestScore :"
		// + tmpBestScore);
		if (currentScore > tmpBestScore) {
			SharedPreferences.Editor editor = sharePref.edit();
			editor.putInt("bestScore", currentScore);
			editor.commit();
		}

		super.onPause();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		System.exit(0);
	}

	/*
	 * 显示当前游戏得分
	 */
	public void showScore() {
		tvCurrentScore.setText(currentScore + "");
	}

	/*
	 * 显示当前最高游戏得分
	 */
	public void showBestScore() {
		tvBestScore.setText(bestScore + "");
	}
	
	/*
	 * 对当前游戏得分进行清零
	 */
	public void clearScore() {
		currentScore = 0;
		showScore();
	}

	/*
	 * 更新当前游戏得分、最高分
	 */
	public void addScore(int score) {
		currentScore += score;
		showScore();
		if (currentScore > bestScore) {
			bestScore = currentScore;
			showBestScore();
		}
	}
}
