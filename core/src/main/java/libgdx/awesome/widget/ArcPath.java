package libgdx.awesome.widget;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class ArcPath implements Path<Vector2> {
    private Vector2 center;
    private float radius;
    private float offset;
    private float arc;
    public ArcPath(Vector2 center,float radius,float offset, float arc){
        this.center=center;
        this.radius=radius;
        this.offset=offset;
        this.arc=arc;
    }


    @Override
    public Vector2 derivativeAt(Vector2 out, float t) {
        float arcAngle=offset+t*arc;
        out.x=center.x+radius* MathUtils.cosDeg(arcAngle);
        out.y=center.y+radius* MathUtils.sinDeg(arcAngle);
        return out;
    }

    @Override
    public Vector2 valueAt(Vector2 out, float t) {
        float arcAngle=offset+t*arc;
        out.x=center.x+radius* MathUtils.cosDeg(arcAngle);
        out.y=center.y+radius* MathUtils.sinDeg(arcAngle);
        return out;
    }

    @Override
    public float approximate(Vector2 v) {
        return 0;
    }

    @Override
    public float locate(Vector2 v) {
        return 0;
    }

    @Override
    public float approxLength(int samples) {
        return MathUtils.PI2*radius*arc/360.0f;
    }
}
