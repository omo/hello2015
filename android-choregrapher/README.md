
Findings:

 * `onTouchEvent()` is syncing to VSYNC.
 * `Chroreographer` callback is called even if there is no need to repaint. Probably because this is for implementing animations.
 * The order is `onTouchEvnet` -> `FrameCallback` -> `onDraw()`.
