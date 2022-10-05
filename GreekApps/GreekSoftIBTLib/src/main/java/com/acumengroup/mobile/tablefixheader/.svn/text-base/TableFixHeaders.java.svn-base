package com.acumengroup.mobile.tablefixheader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.acumengroup.mobile.R;
import com.acumengroup.mobile.tablefixheader.adapters.TableAdapter;
import com.acumengroup.mobile.tablefixheader.custom.CustomDataSetObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * This view shows a watch_table which can scroll in both directions. Also still
 * leaves the headers fixed.
 * <p/>
 * Created by Arcadia
 */
public class TableFixHeaders extends ViewGroup {
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SCROLLING = 1;
    private final Handler handler = new Handler();
    private final List<View> headerRowViewList;
    private final List<Integer> visibleColumnIndexList;
    private final List<View> firstColumnViewList;
    private final List<List<View>> bodyViewTable;
    private final List<Integer> visibleRowIndexList;
    private final int minimumVelocity;
    private final int maximumVelocity;
    private final Flinger flinger;
    private final int mTouchSlop;
    private boolean mIsScrolling = false;
    private TableFixHeaderScrollListener scrollListener;
    private int currentX;
    private int currentY;
    private TableAdapter adapter;
    private int scrollX;
    private int scrollY;
    private int firstRow;
    private final Runnable callListener = new Runnable() {
        @Override
        public void run() {
            if (scrollListener != null)
                scrollListener.onTableScrollData(SCROLL_STATE_IDLE, getFirstRow(), getVisibleRowCount());
        }
    };
    private int firstColumn;
    private int[] widths;
    private int[] heights;
    @SuppressWarnings("unused")
    private View headView;
    private int rowCount;
    private int columnCount;
    private int width;
    private int height;
    private Recycler recycler;
    private TableAdapterDataSetObserver tableAdapterDataSetObserver;
    private boolean needRelayout;
    private VelocityTracker velocityTracker;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public TableFixHeaders(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public TableFixHeaders(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.headView = null;
        this.headerRowViewList = new ArrayList<>();
        this.visibleColumnIndexList = new ArrayList<>();
        this.firstColumnViewList = new ArrayList<>();
        this.bodyViewTable = new ArrayList<>();
        this.visibleRowIndexList = new ArrayList<>();

        this.needRelayout = true;

        this.flinger = new Flinger(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.maximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public void setOnScrollListener(TableFixHeaderScrollListener listener) {
        scrollListener = listener;
    }

    /**
     * Returns the adapter currently associated with this widget.
     *
     * @return The adapter used to provide this view's content.
     */
    public TableAdapter getAdapter() {
        return adapter;
    }

    /**
     * Sets the data behind this TableFixHeaders.
     *
     * @param adapter The TableAdapter which is responsible for maintaining the data
     *                backing this list and for producing a view to represent an
     *                item in that data set.
     */
    public void setAdapter(TableAdapter adapter) {
        if (this.adapter != null) {
            this.adapter.unregisterDataSetObserver(tableAdapterDataSetObserver);
        }

        this.adapter = adapter;
        tableAdapterDataSetObserver = new TableAdapterDataSetObserver();
        this.adapter.registerDataSetObserver(tableAdapterDataSetObserver);

        this.recycler = new Recycler(adapter.getViewTypeCount());

        scrollX = 0;
        scrollY = 0;
        firstColumn = 0;
        firstRow = 0;

        needRelayout = true;
        requestLayout();
    }

    /**
     * Returns the index of first visible row.
     *
     * @return The index of first visible row.
     */
    public int getFirstRow() {
        return firstRow;
    }

    public int getVisibleColumnCount() {
        return visibleColumnIndexList.size();
    }

    public int getVisibleRowCount() {
        return visibleRowIndexList.size();
    }

    public List<Integer> getVisibleColumnIndexList() {
        return visibleColumnIndexList;
    }

    public List<Integer> getVisibleRowIndexList() {
        return visibleRowIndexList;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        boolean retVal = false;

        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the scroll.
            mIsScrolling = false;
            retVal = false; // Do not intercept touch event, let the child handle it
            Log.d("onInterceptTouchEvent", "action_up || action_cancel");
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                currentX = (int) ev.getRawX();
                currentY = (int) ev.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mIsScrolling) {
                    // We're currently scrolling, so yes, intercept the
                    // touch event!
                    retVal = true;
                    Log.d("onInterceptTouchEvent", "mIsScrolling true");
                }
                final int xDiff = Math.abs(currentX - (int) ev.getRawX());
                final int yDiff = Math.abs(currentY - (int) ev.getRawY());
                if (xDiff > mTouchSlop || yDiff > mTouchSlop) {
                    mIsScrolling = true;
                    Log.d("Scrolling", "We Started Scrolling");
                    retVal = true;
                }
                break;
            }
        }
        Log.d("onInterceptTouchEvent", "Returned" + retVal);
        return retVal;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerId = event.getPointerId(event.getActionIndex());
        if (velocityTracker == null) { // If we do not have velocity tracker
            velocityTracker = VelocityTracker.obtain(); // then get one
        }
        velocityTracker.addMovement(event); // add this movement to it

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!flinger.isFinished()) { // If scrolling, then stop now
                    flinger.forceFinished();
                }
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int x2 = (int) event.getRawX();
                final int y2 = (int) event.getRawY();

                final int diffX = currentX - x2;
                final int diffY = currentY - y2;

                if (Math.abs(currentX - x2) > Math.abs(currentY - y2)) {
                    currentY = y2;
                    currentX = x2;
                    scrollBy(diffX, 0);
                } else {
                    currentY = y2;
                    currentX = x2;
                    scrollBy(0, diffY);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
                int velocityX = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, pointerId);
                int velocityY = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, pointerId);

                if (Math.abs(velocityY) > minimumVelocity) {
                    flinger.start(getActualScrollX(), getActualScrollY(), velocityX, velocityY, getMaxScrollX(), getMaxScrollY());
                    handler.removeCallbacks(callListener);
                    handler.postDelayed(callListener, flinger.getDuration());
                } else if (Math.abs(velocityX) > minimumVelocity) {
                    flinger.start(getActualScrollX(), getActualScrollY(), velocityX, velocityY, getMaxScrollX(), getMaxScrollY());
                } else {
                    if (mIsScrolling) {
                        handler.removeCallbacks(callListener);
                        handler.postDelayed(callListener, 100);
                    }
                    mIsScrolling = false;
                }
                // If the velocity less than threshold recycle the tracker
                if (this.velocityTracker != null) {
                    this.velocityTracker.recycle();
                    this.velocityTracker = null;
                }
                break;
            }
        }
        return false;
    }


    @Override
    public void scrollTo(int x, int y) {
        if (needRelayout) {
            scrollX = x;
            firstColumn = 0;

            scrollY = y;
            firstRow = 0;
        } else {
            scrollBy(x - sumArray(widths, 1, firstColumn) - scrollX, y - sumArray(heights, 1, firstRow) - scrollY);
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollX += x;
        scrollY += y;

        if (needRelayout) {
            return;
        }

        scrollBounds();

		/*
         * TODO Improve the algorithm. Think big diagonal movements. If we are
		 * in the top left corner and scrollBy to the opposite corner. We will
		 * have created the views from the top right corner on the X part and we
		 * will have eliminated to generate the right at the Y.
		 */
        if (scrollX == 0) {
            // no op
        } else if (scrollX > 0) {
            while (widths[firstColumn + 1] < scrollX) {
                if (!headerRowViewList.isEmpty()) {
                    removeLeft();
                }
                scrollX -= widths[firstColumn + 1];
                firstColumn++;
            }
            while (getFilledWidth() < width) {
                addRight();
            }
        } else {
            while (!headerRowViewList.isEmpty() && getFilledWidth() - widths[firstColumn + headerRowViewList.size()] >= width) {
                removeRight();
            }
            if (headerRowViewList.isEmpty()) {
                while (scrollX < 0) {
                    firstColumn--;
                    scrollX += widths[firstColumn + 1];
                }
                while (getFilledWidth() < width) {
                    addRight();
                }
            } else {
                while (0 > scrollX) {
                    addLeft();
                    firstColumn--;
                    scrollX += widths[firstColumn + 1];
                }
            }
        }

        if (scrollY == 0) {
            // no op
        } else if (scrollY > 0) {
            while (heights[firstRow + 1] < scrollY) {
                if (!firstColumnViewList.isEmpty()) {
                    removeTop();
                }
                scrollY -= heights[firstRow + 1];
                firstRow++;
            }
            while (getFilledHeight() < height) {
                addBottom();
            }
        } else {
            while (!firstColumnViewList.isEmpty() && getFilledHeight() - heights[firstRow + firstColumnViewList.size()] >= height) {
                removeBottom();
            }
            if (firstColumnViewList.isEmpty()) {
                while (scrollY < 0) {
                    firstRow--;
                    scrollY += heights[firstRow + 1];
                }
                while (getFilledHeight() < height) {
                    addBottom();
                }
            } else {
                while (0 > scrollY) {
                    addTop();
                    firstRow--;
                    scrollY += heights[firstRow + 1];
                }
            }
        }

        repositionViews();

        //shadowsVisibility();
    }

    private int getActualScrollX() {
        return scrollX + sumArray(widths, 1, firstColumn);
    }

    private int getActualScrollY() {
        return scrollY + sumArray(heights, 1, firstRow);
    }

    private int getMaxScrollX() {
        return Math.max(0, sumArray(widths) - width);
    }

    private int getMaxScrollY() {
        return Math.max(0, sumArray(heights) - height);
    }

    private int getFilledWidth() {
        return widths[0] + sumArray(widths, firstColumn + 1, headerRowViewList.size()) - scrollX;
    }

    private int getFilledHeight() {
        return heights[0] + sumArray(heights, firstRow + 1, firstColumnViewList.size()) - scrollY;
    }

    private void addLeft() {
        addLeftOrRight(firstColumn - 1, 0);
    }

    private void addTop() {
        addTopAndBottom(firstRow - 1, 0);
    }

    private void addRight() {
        final int size = headerRowViewList.size();
        addLeftOrRight(firstColumn + size, size);
    }

    private void addBottom() {
        final int size = firstColumnViewList.size();
        addTopAndBottom(firstRow + size, size);
    }

    private void addLeftOrRight(int column, int index) {
        View view = makeView(-1, column, widths[column + 1], heights[0]);
        headerRowViewList.add(index, view);
        visibleColumnIndexList.add(index, column);
        int i = firstRow;
        for (List<View> list : bodyViewTable) {
            view = makeView(i, column, widths[column + 1], heights[i + 1]);
            list.add(index, view);
            i++;
        }
    }

    private void addTopAndBottom(int row, int index) {
        View view = makeView(row, -1, widths[0], heights[row + 1]);
        firstColumnViewList.add(index, view);
        visibleRowIndexList.add(index, row);
        List<View> list = new ArrayList<>();
        final int size = headerRowViewList.size() + firstColumn;
        for (int i = firstColumn; i < size; i++) {
            view = makeView(row, i, widths[i + 1], heights[row + 1]);
            list.add(view);
        }
        bodyViewTable.add(index, list);
    }

    private void removeLeft() {
        removeLeftOrRight(0);
    }

    private void removeTop() {
        removeTopOrBottom(0);
    }

    private void removeRight() {
        removeLeftOrRight(headerRowViewList.size() - 1);
    }

    private void removeBottom() {
        removeTopOrBottom(firstColumnViewList.size() - 1);
    }

    private void removeLeftOrRight(int position) {
        removeView(headerRowViewList.remove(position));
        visibleColumnIndexList.remove(position);
        for (List<View> list : bodyViewTable) {
            removeView(list.remove(position));
        }
    }

    private void removeTopOrBottom(int position) {
        removeView(firstColumnViewList.remove(position));
        visibleRowIndexList.remove(position);
        List<View> remove = bodyViewTable.remove(position);
        for (View view : remove) {
            removeView(view);
        }
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);

        final int typeView = (Integer) view.getTag(R.id.tag_type_view);
        if (typeView != TableAdapter.IGNORE_ITEM_VIEW_TYPE) {
            recycler.addRecycledView(view, typeView);
        }
    }

    private void repositionViews() {
        int left, top, right, bottom, i;

        left = widths[0] - scrollX;
        i = firstColumn;
        for (View view : headerRowViewList) {
            right = left + widths[++i];
            view.layout(left, 0, right, heights[0]);
            left = right;
        }

        top = heights[0] - scrollY;
        i = firstRow;
        for (View view : firstColumnViewList) {
            bottom = top + heights[++i];
            view.layout(0, top, widths[0], bottom);
            top = bottom;
        }

        top = heights[0] - scrollY;
        i = firstRow;
        for (List<View> list : bodyViewTable) {
            bottom = top + heights[++i];
            left = widths[0] - scrollX;
            int j = firstColumn;
            for (View view : list) {
                right = left + widths[++j];
                view.layout(left, top, right, bottom);
                left = right;
            }
            top = bottom;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        final int w;
        final int h;

        if (adapter != null) {
            this.rowCount = adapter.getRowCount();
            this.columnCount = adapter.getColumnCount();

            widths = new int[columnCount + 1];
            for (int i = -1; i < columnCount; i++) {
                widths[i + 1] += adapter.getWidth(i);
            }
            heights = new int[rowCount + 1];
            for (int i = -1; i < rowCount; i++) {
                heights[i + 1] += adapter.getHeight(i);
            }

            if (widthMode == MeasureSpec.AT_MOST) {
                w = Math.min(widthSize, sumArray(widths));
            } else if (widthMode == MeasureSpec.UNSPECIFIED) {
                w = sumArray(widths);
            } else {
                w = widthSize;
                int sumArray = sumArray(widths);
                if (sumArray < widthSize) {
                    final float factor = widthSize / (float) sumArray;
                    for (int i = 1; i < widths.length; i++) {
                        widths[i] = Math.round(widths[i] * factor);
                    }
                    widths[0] = widthSize - sumArray(widths, 1, widths.length - 1);
                }
            }

            if (heightMode == MeasureSpec.AT_MOST) {
                h = Math.min(heightSize, sumArray(heights));
            } else if (heightMode == MeasureSpec.UNSPECIFIED) {
                h = sumArray(heights);
            } else {
                h = heightSize;
            }
        } else {
            if (heightMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
                w = 0;
                h = 0;
            } else {
                w = widthSize;
                h = heightSize;
            }
        }

        if (firstRow >= rowCount || getMaxScrollY() - getActualScrollY() < 0) {
            firstRow = 0;
            scrollY = Integer.MAX_VALUE;
        }
        if (firstColumn >= columnCount || getMaxScrollX() - getActualScrollX() < 0) {
            firstColumn = 0;
            scrollX = Integer.MAX_VALUE;
        }

        setMeasuredDimension(w, h);
    }

    private int sumArray(int[] array) {
        return sumArray(array, 0, array.length);
    }

    private int sumArray(int[] array, int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += array[i];
        }
        return sum;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
            resetTable();

            if (adapter != null) {
                width = r - l;
                height = b - t;

                int left, top, right, bottom;

                headView = makeAndSetup(-1, -1, 0, 0, widths[0], heights[0]);

                scrollBounds();
                //adjustFirstCellsAndScroll();

                left = widths[0] - scrollX;
                for (int i = firstColumn; i < columnCount && left < width; i++) {
                    right = left + widths[i + 1];
                    final View view = makeAndSetup(-1, i, left, 0, right, heights[0]);
                    headerRowViewList.add(view);
                    visibleColumnIndexList.add(i);
                    left = right;
                }

                top = heights[0] - scrollY;
                for (int i = firstRow; i < rowCount && top < height; i++) {
                    bottom = top + heights[i + 1];
                    final View view = makeAndSetup(i, -1, 0, top, widths[0], bottom);
                    firstColumnViewList.add(view);
                    visibleRowIndexList.add(i);
                    top = bottom;
                }

                top = heights[0] - scrollY;
                for (int i = firstRow; i < rowCount && top < height; i++) {
                    bottom = top + heights[i + 1];
                    left = widths[0] - scrollX;
                    List<View> list = new ArrayList<>();
                    for (int j = firstColumn; j < columnCount && left < width; j++) {
                        right = left + widths[j + 1];
                        final View view = makeAndSetup(i, j, left, top, right, bottom);
                        list.add(view);
                        left = right;
                    }
                    bodyViewTable.add(list);
                    top = bottom;
                }

            }
        }
    }

    private void scrollBounds() {
        scrollX = scrollBounds(scrollX, firstColumn, widths, width);
        scrollY = scrollBounds(scrollY, firstRow, heights, height);
    }

    private int scrollBounds(int desiredScroll, int firstCell, int[] sizes, int viewSize) {
        if (desiredScroll == 0) {
            // no op
        } else if (desiredScroll < 0) {
            desiredScroll = Math.max(desiredScroll, -sumArray(sizes, 1, firstCell));
        } else {
            desiredScroll = Math.min(desiredScroll, Math.max(0, sumArray(sizes, firstCell + 1, sizes.length - 1 - firstCell) + sizes[0] - viewSize));
        }
        return desiredScroll;
    }


    private void resetTable() {
        headView = null;
        headerRowViewList.clear();
        visibleColumnIndexList.clear();
        firstColumnViewList.clear();
        bodyViewTable.clear();
        visibleRowIndexList.clear();

        removeAllViews();
    }

    private View makeAndSetup(int row, int column, int left, int top, int right, int bottom) {
        final View view = makeView(row, column, right - left, bottom - top);
        view.layout(left, top, right, bottom);
        return view;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean ret;

        final Integer row = (Integer) child.getTag(R.id.tag_row);
        final Integer column = (Integer) child.getTag(R.id.tag_column);
        // row == null => Shadow view
        if (row == null || (row == -1 && column == -1)) {
            ret = super.drawChild(canvas, child, drawingTime);
        } else {
            canvas.save();
            if (row == -1) {
                canvas.clipRect(widths[0], 0, canvas.getWidth(), canvas.getHeight());
            } else if (column == -1) {
                canvas.clipRect(0, heights[0], canvas.getWidth(), canvas.getHeight());
            } else {
                canvas.clipRect(widths[0], heights[0], canvas.getWidth(), canvas.getHeight());
            }

            ret = super.drawChild(canvas, child, drawingTime);
            canvas.restore();
        }
        return ret;
    }

    private View makeView(int row, int column, int w, int h) {
        final int itemViewType = adapter.getItemViewType(row, column);
        final View recycledView;
        if (itemViewType == TableAdapter.IGNORE_ITEM_VIEW_TYPE) {
            recycledView = null;
        } else {
            recycledView = recycler.getRecycledView(itemViewType);
        }
        final View view = adapter.getView(row, column, recycledView, this);
        view.setTag(R.id.tag_type_view, itemViewType);
        view.setTag(R.id.tag_row, row);
        view.setTag(R.id.tag_column, column);
        view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
        addTableView(view, row, column);
        return view;
    }

    private void addTableView(View view, int row, int column) {
        if (row == -1 && column == -1) {
            addView(view, getChildCount() - 4);
        } else if (row == -1 || column == -1) {
            addView(view, getChildCount() - 5);
        } else {
            addView(view, 0);
        }
    }

    private class TableAdapterDataSetObserver extends CustomDataSetObserver {

        @Override
        public void onChanged() {
            needRelayout = true;
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            // Do nothing
        }

        public void onChangeAt(int index) {
            Log.d("Index Changed", "index:" + index);
            //if (!needRelayout) updateLayout(index);
        }
    }

    // http://stackoverflow.com/a/6219382/842697
    private class Flinger implements Runnable {
        private final Scroller scroller;

        private int lastX = 0;
        private int lastY = 0;

        Flinger(Context context) {
            scroller = new Scroller(context);
        }

        void start(int initX, int initY, int initialVelocityX, int initialVelocityY, int maxX, int maxY) {
            scroller.fling(initX, initY, initialVelocityX, initialVelocityY, 0, maxX, 0, maxY);

            lastX = initX;
            lastY = initY;
            post(this);
        }

        public int getDuration() {
            return scroller.getDuration();
        }

        public void run() {
            if (scroller.isFinished()) {
                return;
            }

            boolean more = scroller.computeScrollOffset();
            int x = scroller.getCurrX();
            int y = scroller.getCurrY();
            int diffX = lastX - x;
            int diffY = lastY - y;
            if (diffX != 0 || diffY != 0) {
                scrollBy(diffX, diffY);
                lastX = x;
                lastY = y;
            }

            if (more) {
                post(this);
            }
        }

        boolean isFinished() {
            return scroller.isFinished();
        }

        void forceFinished() {
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
        }
    }
}