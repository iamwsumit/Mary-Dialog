package com.sumit1334.marydialogs.Dragging;

import androidx.annotation.NonNull;

public interface ViewAnimator<D extends DraggableView> {

    boolean animateExit(@NonNull final D draggableView, final DraggableView.Direction direction, int duration);

    boolean animateToOrigin(@NonNull final D draggableView, final int duration);

    void update(@NonNull final D draggableView, float percentX, float percentY);

    interface Listener {
        void animationStarted();
        void animationEnd();
    }
}