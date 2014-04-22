package com.star.game2048;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class Card extends FrameLayout {
	
	private int num = 0;
//	private TextView lable;
	private CornerTextView lable;
	
	public Card(Context context) {
		super(context);
		initCard();
	}

	public Card(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCard();
	}

	public Card(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initCard();
	}

	private void initCard(){
		
		lable = new CornerTextView(getContext());
		
		lable.setBackgroundColor(0x33FFFFFF);
		
//		lable = new TextView(getContext());
//		lable.setGravity(Gravity.CENTER);
//		lable.setBackgroundColor(0x33ffffff);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.setMargins(20, 20, 0, 0);
		addView(lable,lp);
		setNum(0);
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		if(num <= 0){
			lable.setText("");
			lable.setBackgroundColor(getResources().getColor(R.color.titledef));
		}else {
			
			switch (num) {
			case 2:
				lable.setTextSize(32);
				lable.setTextColor(getResources().getColor(R.color.tile2ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile2bk));
				break;
			case 4:
				lable.setTextSize(32);
				lable.setTextColor(getResources().getColor(R.color.tile4ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile4bk));
				break;
			case 8:
				lable.setTextSize(32);
				lable.setTextColor(getResources().getColor(R.color.tile8ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile8bk));
				break;
			case 16:
				lable.setTextSize(30);
				lable.setTextColor(getResources().getColor(R.color.tile16ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile16bk));
				break;
			case 32:
				lable.setTextSize(30);
				lable.setTextColor(getResources().getColor(R.color.tile32ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile32bk));
				break;
			case 64:
				lable.setTextSize(30);
				lable.setTextColor(getResources().getColor(R.color.tile64ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile64bk));
				break;
			case 128:
				lable.setTextSize(28);
				lable.setTextColor(getResources().getColor(R.color.tile128ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile128bk));
				break;
			case 256:
				lable.setTextSize(28);
				lable.setTextColor(getResources().getColor(R.color.tile256ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile256bk));
				break;
			case 512:
				lable.setTextSize(28);
				lable.setTextColor(getResources().getColor(R.color.tile512ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile512bk));
				break;
			case 1024:
				lable.setTextSize(24);
				lable.setTextColor(getResources().getColor(R.color.tile1024ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile1024bk));
				break;
			case 2048:
				lable.setTextSize(23);
				lable.setTextColor(getResources().getColor(R.color.tile2048ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile2048bk));
				break;
			default : 
				lable.setTextSize(20);
				lable.setTextColor(getResources().getColor(R.color.tile2048ft));
				lable.setBackgroundColor(getResources().getColor(R.color.tile2048bk));
				
			}
			
			lable.setText(num+"");
		}
	}
	
	public boolean equals(Card c) {
		return (getNum()!=0) && (c.getNum()==getNum());
	}
}
