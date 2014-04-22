package com.star.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity {

	// private Button startGame;
	private static MainActivity mainActivity = null;
	private int currentScore = 0;
	private int bestScore = 0;
//	private TextView tvCurrentScore;
//	private TextView tvBestScore;
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
		setContentView(R.layout.activity_main);

		tvCurrentScore = (CornerTextView) findViewById(R.id.tvCurrentScore);
	//	tvCurrentScore = (TextView) findViewById(R.id.tvCurrentScore);
		tvCurrentScore.setBackgroundColor(0xFFBBADA0);
		tvCurrentScore.setTextColor(0xFFEEE4DA);
		tvCurrentScore.setInsertStaticString("SCORE");
		
		tvBestScore = (CornerTextView) findViewById(R.id.tvBestScore);
	//	tvBestScore = (TextView) findViewById(R.id.tvBestScore);
		tvBestScore.setBackgroundColor(0xFFBBADA0);
		tvBestScore.setTextColor(0xFFEEE4DA);
		tvBestScore.setInsertStaticString("BEST");

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

	public void quitGame(){
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && GameView.getGameViewCls().isGameStarted()) {
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
		SharedPreferences sharePref = getSharedPreferences(
				"shared_file", 0);
		int tmpBestScore = sharePref.getInt(
				"bestScore", 0);
//		System.out
//		.println("onKeyDown----> secode bestScore :"
//				+ bestScore
//				+ "	currentScore :"
//				+ currentScore
//				+ "	tmpBestScore :"
//				+ tmpBestScore);
		if (currentScore > tmpBestScore) {
			SharedPreferences.Editor editor = sharePref
					.edit();
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

	public void showScore() {
		tvCurrentScore.setText(currentScore + "");
	}

	public void showBestScore() {
		tvBestScore.setText(bestScore + "");
	}

	public void clearScore() {
		currentScore = 0;
		showScore();
	}

	public void addScore(int score) {
		currentScore += score;
		showScore();
		if (currentScore > bestScore) {
			bestScore = currentScore;
			showBestScore();
		}
	}
}
