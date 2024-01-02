package oneulmwohaji.domain.post.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

@Getter
@AllArgsConstructor
@Builder
public class UserGeometryInfoRequest {
    @NotNull
    private double x;
    @NotNull
    private double y;

    @NotNull
    private double range;

    public String toWKT() throws ParseException {
        String pointWKT = String.format("POINT(%s %s)", y, x);
        Point point = (Point) new WKTReader().read(pointWKT);
        WKTWriter wktWriter = new WKTWriter();
        return wktWriter.write(point);
    }
}
