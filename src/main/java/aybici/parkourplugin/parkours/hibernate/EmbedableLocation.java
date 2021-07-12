package aybici.parkourplugin.parkours.hibernate;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class EmbedableLocation {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String worldName;

    @Transient
    private Location location;

    public Location getLocation() {
        if(location == null)
            location = new Location(Bukkit.getWorld(worldName), x,y,z,yaw,pitch);
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
    }
}
