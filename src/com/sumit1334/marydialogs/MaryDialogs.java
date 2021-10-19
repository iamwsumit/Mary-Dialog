package com.sumit1334.marydialogs;

import android.app.Activity;
import android.content.Context;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.sumit1334.marydialogs.Dialog.MaryPopup;

import java.io.IOException;
import java.util.HashMap;

public class MaryDialogs extends AndroidNonvisibleComponent implements Component {
    private final Context context;
    private final HashMap<String, MaryPopup> popups = new HashMap<>();

//  Properties

    private int backgroundColor;
    private int blackOverlayColor;
    private boolean draggable;
    private boolean centre;
    private boolean scaleDownDragging;
    private boolean fadeOutDragging;
    private int openDuration;
    private int closeDuration;
    private boolean shadow;
    private boolean inlineMove;

    public MaryDialogs(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
        BackgroundColor(0);
        BlackOverlayColor(COLOR_DKGRAY);
        Draggable(false);
        ShowOnCentre(true);
        ScaleDownDragging(false);
        FadeOutDragging(false);
        OpenDuration(200);
        CloseDuration(200);
        Shadow(false);
        InlineMove(false);
    }

    @SimpleProperty(description = "Set the background color of the dialog")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_DKGRAY)
    public void BackgroundColor(int color) {
        this.backgroundColor = color;
    }

    @SimpleProperty
    public int BackgroundColor() {
        return backgroundColor;
    }

    @SimpleProperty(description = "Set the overlay color of the dialog")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_DKGRAY)
    public void BlackOverlayColor(int color) {
        this.blackOverlayColor = color;
    }

    @SimpleProperty
    public int BlackOverlayColor() {
        return blackOverlayColor;
    }

    @SimpleProperty(description = "If this enabled then the dialog can be dragged by the user")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void Draggable(boolean draggable) {
        this.draggable = draggable;
    }

    @SimpleProperty
    public boolean Draggable() {
        return draggable;
    }

    @SimpleProperty(description = "If enabled then the dialog will be draggable inline-ly")
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void InlineMove(boolean inlineMove) {
        this.inlineMove = inlineMove;
    }

    @SimpleProperty
    public boolean InlineMove() {
        return inlineMove;
    }

    @SimpleProperty(description = "If enabled then the dialog will be shown at centre of the screen")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "True")
    public void ShowOnCentre(boolean centre) {
        this.centre = centre;
    }

    @SimpleProperty
    public boolean ShowOnCentre() {
        return centre;
    }

    @SimpleProperty(description = "Enable the shadow of the dialog")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void Shadow(boolean shadow) {
        this.shadow = shadow;
    }

    @SimpleProperty
    public boolean Shadow() {
        return shadow;
    }

    @SimpleProperty(description = "If enabled then the dialog will be animated while dismissing by dragging")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void ScaleDownDragging(boolean scaleDownDragging) {
        this.scaleDownDragging = scaleDownDragging;
    }

    @SimpleProperty
    public boolean ScaleDownDragging() {
        return scaleDownDragging;
    }

    @SimpleProperty(description = "If enabled then the dialog will be animated by fade out technique while dismissing")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, defaultValue = "False")
    public void FadeOutDragging(boolean fadeOutDragging) {
        this.fadeOutDragging = fadeOutDragging;
    }

    @SimpleProperty
    public boolean FadeOutDragging() {
        return fadeOutDragging;
    }

    @SimpleProperty(description = "Set the duration of opening of dialog")
    @DesignerProperty(defaultValue = "200", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void OpenDuration(int openDuration) {
        this.openDuration = openDuration;
    }

    @SimpleProperty
    public int OpenDuration() {
        return openDuration;
    }

    @SimpleProperty(description = "Set the closing duration of the dialog")
    @DesignerProperty(defaultValue = "200", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void CloseDuration(int closeDuration) {
        this.closeDuration = closeDuration;
    }

    @SimpleProperty
    public int CloseDuration() {
        return closeDuration;
    }

    @SimpleEvent(description = "This event raises when the dialog is dismissed")
    public void Dismissed(final String id) {
        EventDispatcher.dispatchEvent(this, "Dismissed", id);
    }

    @SimpleFunction(description = "This block show the dialog with customised properties")
    public void Show(final String id, final AndroidViewComponent originView, final AndroidViewComponent dialogView, final boolean cancellable) throws IOException {
        if (id != null && originView != null && dialogView != null) {
            MaryPopup popup = MaryPopup.with((Activity) context)
                    .cancellable(cancellable)
                    .draggable(draggable)
                    .fadeOutDragging(fadeOutDragging)
                    .blackOverlayColor(blackOverlayColor)
                    .backgroundColor(backgroundColor)
                    .scaleDownDragging(scaleDownDragging)
                    .openDuration(openDuration)
                    .closeDuration(closeDuration)
                    .center(centre)
                    .shadow(shadow)
                    .inlineMove(inlineMove)
                    .from(originView.getView())
                    .content(dialogView.getView());
            popup.show();
            popup.setListener(new MaryPopup.Listener() {
                @Override
                public void dismissed() {
                    Dismissed(id);
                }
            });
            popups.put(id, popup);
        } else
            throw new IllegalArgumentException("Provide right arguments");
    }

    @SimpleFunction(description = "This block dismisses the shown dialog with id")
    public void Dismiss(final String id) {
        this.popups.get(id).close(scaleDownDragging);
    }

    @SimpleFunction(description = "Returns true if dialog referenced to given id is showing")
    public final boolean IsShowing(String id) {
        return this.popups.get(id).isOpened();
    }
}
