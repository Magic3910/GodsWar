package godswar.godswar.utils.base.logging;

import org.bukkit.ChatColor;

public abstract class LogType {

	public static final LogType DEBUG = new LogType(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "디버그" + ChatColor.DARK_GRAY + "]") {
		@Override
		protected boolean isLoggable() {
			return true;
		}
	};

	public static final LogType ERROR = new LogType(ChatColor.DARK_RED + "[" + ChatColor.RED + "오류" + ChatColor.DARK_RED + "]") {
		@Override
		protected boolean isLoggable() {
			return true;
		}
	};

	final String prefix;

	private LogType(final String prefix) {
		this.prefix = prefix;
	}

	abstract boolean isLoggable();

}
