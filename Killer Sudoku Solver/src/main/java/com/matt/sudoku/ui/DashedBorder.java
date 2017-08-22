package com.matt.sudoku.ui;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.ConstructorProperties;
import javax.swing.border.Border;

public class DashedBorder extends AbstractBorder {

	public static Border createDashedBorder(Paint paint, float thickness, float length, float spacing,
			boolean rounded, boolean topNeighbour, boolean bottomNeighbour, boolean leftNeighbour, boolean rightNeighbour) {
		boolean shared = !rounded && (paint == null) && (thickness == 1.0f) && (length == 1.0f) && (spacing == 1.0f);
		if (thickness < 1.0f) {
			throw new IllegalArgumentException("thickness is less than 1");
		}
		if (length < 1.0f) {
			throw new IllegalArgumentException("length is less than 1");
		}
		if (spacing < 0.0f) {
			throw new IllegalArgumentException("spacing is less than 0");
		}
		int cap = rounded ? BasicStroke.CAP_ROUND : BasicStroke.CAP_SQUARE;
		int join = rounded ? BasicStroke.JOIN_ROUND : BasicStroke.JOIN_MITER;
		float[] array = { thickness * (length - 1.0f), thickness * (spacing + 1.0f) };
		Border border = new DashedBorder(new BasicStroke(thickness, cap, join, thickness * 2.0f, array, 0.0f), paint, topNeighbour, bottomNeighbour, leftNeighbour, rightNeighbour);
		return border;
	}

	private final BasicStroke stroke;
	private final Paint paint;
	private final boolean topNeighbour;
	private final boolean bottomNeighbour;
	private final boolean leftNeighbour;
	private final boolean rightNeighbour;

	/**
	 * Creates a border of the specified {@code stroke} and {@code paint}. If
	 * the specified {@code paint} is {@code null}, the component's foreground
	 * color will be used to render the border.
	 *
	 * @param stroke
	 *            the {@link BasicStroke} object used to stroke a shape
	 * @param paint
	 *            the {@link Paint} object used to generate a color
	 *
	 * @throws NullPointerException
	 *             if the specified {@code stroke} is {@code null}
	 */
	@ConstructorProperties({ "stroke", "paint" })
	public DashedBorder(BasicStroke stroke, Paint paint, boolean topNeighbour, boolean bottomNeighbour, boolean leftNeighbour, boolean rightNeighbour) {
		if (stroke == null) {
			throw new NullPointerException("border's stroke");
		}
		this.stroke = stroke;
		this.paint = paint;
		this.topNeighbour = topNeighbour;
		this.bottomNeighbour = bottomNeighbour;
		this.leftNeighbour = leftNeighbour;
		this.rightNeighbour = rightNeighbour;
	}

	/**
	 * Paints the border for the specified component with the specified position
	 * and size. If the border was not specified with a {@link Paint} object,
	 * the component's foreground color will be used to render the border. If
	 * the component's foreground color is not available, the default color of
	 * the {@link Graphics} object will be used.
	 *
	 * @param c
	 *            the component for which this border is being painted
	 * @param g
	 *            the paint graphics
	 * @param x
	 *            the x position of the painted border
	 * @param y
	 *            the y position of the painted border
	 * @param width
	 *            the width of the painted border
	 * @param height
	 *            the height of the painted border
	 *
	 * @throws NullPointerException
	 *             if the specified {@code g} is {@code null}
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		float size = this.stroke.getLineWidth();
		if (size > 0.0f) {
			g = g.create();
			if (g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(this.stroke);
				g2d.setPaint(this.paint != null ? this.paint : c == null ? null : c.getForeground());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int gap = 10;
				float x1 = x + size / 2 + (leftNeighbour ? 0 : gap);
				float y1 = y + size / 2 + (topNeighbour ? 0 : gap);
				float x2 = width - size - (rightNeighbour ? 0 : gap);
				float y2 = height - size - (bottomNeighbour ? 0 : gap);
				// System.out.println(String.format("Rectangle2D.Float(x1=%s, y1=%s, x2=%s, y2=%s) x=%s y=%s, size=%s, width=%s, height=%s", x1, y1, x2, y2, x, y, size, width, height));
				//g2d.draw(new Rectangle2D.Float(x1, y1, x2, y2));
				// top
				if (topNeighbour == false) g2d.draw(new Line2D.Float(x1, y1, x2, y1));
				// bottom
				if (bottomNeighbour == false) g2d.draw(new Line2D.Float(x1, y2, x2, y2));
				// left
				if (leftNeighbour == false) g2d.draw(new Line2D.Float(x1, y1, x1, y2));
				// right
				if (rightNeighbour == false) g2d.draw(new Line2D.Float(x2, y1, x2, y2));

				//				g2d.draw(new Line2D.Float(x1, x2, y2, y2));
			}
			g.dispose();
		}
	}

	/**
	 * Reinitializes the {@code insets} parameter with this border's current
	 * insets. Every inset is the smallest (closest to negative infinity)
	 * integer value that is greater than or equal to the line width of the
	 * stroke that is used to paint the border.
	 *
	 * @param c
	 *            the component for which this border insets value applies
	 * @param insets
	 *            the {@code Insets} object to be reinitialized
	 * @return the reinitialized {@code insets} parameter
	 *
	 * @throws NullPointerException
	 *             if the specified {@code insets} is {@code null}
	 *
	 * @see Math#ceil
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		int size = (int) Math.ceil(this.stroke.getLineWidth());
		insets.set(size, size, size, size);
		return insets;
	}

	/**
	 * Returns the {@link BasicStroke} object used to stroke a shape during the
	 * border rendering.
	 *
	 * @return the {@link BasicStroke} object
	 */
	public BasicStroke getStroke() {
		return this.stroke;
	}

	/**
	 * Returns the {@link Paint} object used to generate a color during the
	 * border rendering.
	 *
	 * @return the {@link Paint} object or {@code null} if the {@code paint}
	 *         parameter is not set
	 */
	public Paint getPaint() {
		return this.paint;
	}
}