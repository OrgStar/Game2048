package com.star.game2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class CornerTextView extends TextView {

	private Paint mPain = null;

	private int cornerBkColor;
	private int cornerTextColor;
	private float cornerTextSize;
	private CharSequence cornerString;
	private String insertStaticString;

	public CornerTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initCornerTextView();
	}

	public CornerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initCornerTextView();
	}

	public CornerTextView(Context context) {
		super(context);

		initCornerTextView();
	}

	private void initCornerTextView() {
		System.out.println("CornerTextView--------->initCornerTextView");
		mPain = new Paint(Paint.ANTI_ALIAS_FLAG);

		cornerBkColor = 0xFFBBADA0;
		cornerTextColor = 0xFFEEE4DA;
		cornerTextSize = 20;
		cornerString = "";
		insertStaticString = null;

	}

	public void setInsertStaticString(String insertStaticString) {
		this.insertStaticString = insertStaticString;
	}
	
	@Override
	public void setGravity(int gravity) {
		// TODO Auto-generated method stub
		super.setGravity(gravity);
	}
	
	@Override
	public void setBackgroundColor(int color) {
		System.out.println("CornerTextView--------->setBackgroundColor");
		System.out.println("CornerTextView--------->color " + color);
		cornerBkColor = color;
	}

	@Override
	public void setTextColor(int color) {
		System.out.println("CornerTextView--------->setTextColor");
		System.out.println("CornerTextView--------->color " + color);
		cornerTextColor = color;
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		System.out.println("CornerTextView--------->setText");
		System.out.println("CornerTextView--------->text " + text);
		cornerString = text;
		super.setText(text, type);
	}

	@Override
	public void setTextSize(float size) {
		System.out.println("CornerTextView--------->setTextSize");
		System.out.println("CornerTextView--------->size " + size);
		cornerTextSize = size;
		super.setTextSize(size);
	}

	/*
	 * 获取字符串长度
	 */
	public static int getStringWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	@Override
	public void setWidth(int pixels) {
		// TODO Auto-generated method stub
		super.setWidth(pixels);
	}

	@Override
	public void draw(Canvas canvas) {
		System.out.println("CornerTextView--------->draw");
		super.draw(canvas);
		int width = getWidth();
		int height = getHeight();
		
		RectF rectF = new RectF(0, 0, width, height); // RectF对象
		mPain.setColor(cornerBkColor);
		canvas.drawRoundRect(rectF, 10, 10, mPain); // 绘制圆角矩形
		mPain.setTextSize(cornerTextSize);
		mPain.setColor(cornerTextColor);
		mPain.setTextAlign(Align.CENTER);
		mPain.setFakeBoldText(true);

		
		FontMetrics fontMetrics = mPain.getFontMetrics();
		float fontHeight = fontMetrics.bottom - fontMetrics.top;
		float textBaseY = height - (height - fontHeight) / 2
				- fontMetrics.bottom;
		if (insertStaticString != null) {
			textBaseY = height - (height - fontHeight) / 3 - fontMetrics.bottom;
			float textBaseY1 = textBaseY / 2;
			mPain.setTextSize(21);
			canvas.drawText(insertStaticString, width / 2, textBaseY1, mPain);
			mPain.setTextSize(32);
			int strWidth = Math.max(
					getStringWidth(mPain, (String) cornerString),
					getStringWidth(mPain, insertStaticString));
			setWidth(strWidth + 20);
			canvas.drawText((String) cornerString, width / 2, textBaseY
					+ textBaseY1 / 3, mPain);
		} else {
			canvas.drawText((String) cornerString, width / 2, textBaseY, mPain);
		}

	}

}