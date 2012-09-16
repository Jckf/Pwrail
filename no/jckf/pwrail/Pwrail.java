package no.jckf.pwrail;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Pwrail extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this,this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onVehicleExit(VehicleExitEvent event) {
		Vehicle v = event.getVehicle();

		if (v instanceof Minecart) {
			Location l = v.getLocation();
			l.getWorld().dropItemNaturally(l,new ItemStack(Material.MINECART));
			v.remove();
		}
	}

	@EventHandler
	public void onVehicleUpdate(VehicleUpdateEvent event) {
		Vehicle vehicle = event.getVehicle();

		if (!(vehicle instanceof Minecart)) {
			return;
		}

		Location location = vehicle.getLocation();
		Chunk chunk = location.getChunk();

		if (!chunk.isLoaded()) {
			chunk.load();
		}

		if (!vehicle.isEmpty()) {
			if (location.getBlock().getType() == Material.RAILS) {
				Vector velocity = vehicle.getVelocity();
				Double x = velocity.getX();
				Double z = velocity.getZ();

				if (x > 0 && x < 1.6) {
					velocity.setX(x + 0.1);
				}
				if (z > 0 && z < 1.6) {
					velocity.setZ(z + 0.1);
				}

				if (x < 0 && x > -1.6) {
					velocity.setX(x - 0.1);
				}
				if (z < 0 && z > -1.6) {
					velocity.setZ(z - 0.1);
				}

				vehicle.setVelocity(velocity);
			}
		}
	}
}
