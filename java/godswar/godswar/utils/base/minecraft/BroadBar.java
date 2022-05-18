package godswar.godswar.utils.base.minecraft;

import godswar.godswar.GodsWar;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BroadBar implements Listener {

	private final BossBar bossBar;

	public BroadBar(final String title, final BarColor barColor, final BarStyle barStyle, final BarFlag... flags) {
		this.bossBar = Bukkit.createBossBar(title, barColor, barStyle, flags);
		for (Player player : Bukkit.getOnlinePlayers()) {
			bossBar.addPlayer(player);
		}
		Bukkit.getPluginManager().registerEvents(this, GodsWar.getProvidingPlugin(getClass()));
		bossBar.setVisible(true);
	}

	@EventHandler
	private void onPlayerJoin(final PlayerJoinEvent e) {
		bossBar.addPlayer(e.getPlayer());
	}

	@EventHandler
	private void onPlayerQuit(final PlayerQuitEvent e) {
		bossBar.removePlayer(e.getPlayer());
	}


	public String getTitle() {
		return bossBar.getTitle();
	}

	public void setTitle( String s) {
		bossBar.setTitle(s);
	}


	public BarColor getColor() {
		return bossBar.getColor();
	}

	public void setColor( BarColor barColor) {
		bossBar.setColor(barColor);
	}


	public BarStyle getStyle() {
		return bossBar.getStyle();
	}

	public void setStyle( BarStyle barStyle) {
		bossBar.setStyle(barStyle);
	}

	public void removeFlag( BarFlag barFlag) {
		bossBar.removeFlag(barFlag);
	}

	public void addFlag( BarFlag barFlag) {
		bossBar.addFlag(barFlag);
	}

	public boolean hasFlag( BarFlag barFlag) {
		return bossBar.hasFlag(barFlag);
	}

	public void setProgress(double v) {
		bossBar.setProgress(v);
	}

	public double getProgress() {
		return bossBar.getProgress();
	}

	public void unregister() {
		HandlerList.unregisterAll(this);
		bossBar.removeAll();
	}

}
