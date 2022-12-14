package ml.mops.lobby;

import ml.mops.utils.CHARACTER;
import ml.mops.utils.MAP_BOOLEAN_MODE;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.List;

/**
 * Обработчик комманд администрации (требуют права оператора)
 *
 * @author Kofiy
 */

public class AdminUtils {

	public boolean execCommands(CommandSender sender, Command command, String label, String[] args, Plugin plugin) {
		boolean perms = sender.isOp(); //проверка прав
		if (args == null) { //проверка аргументов на нуль
			args = new String[] {""};
		}

		String arguments = ml.mops.utils.Utils.combineStrings(args, " "); //Объеденение всех аргументов в строку в стиле "arg0 arg1 arg2"
		String commandName = command.getName().toLowerCase(Locale.ROOT); //Получение названия команды

		if (sender instanceof Player player && perms) { //Проверка на права и игрока
			Player target;
			switch (commandName) { //Проверка команды
				case "crank" -> {
					try {
						target = Bukkit.getServer().getPlayer(args[0]); //Попытка получить игрока
					} catch (ArrayIndexOutOfBoundsException expection) {
						if (args[0].toLowerCase(Locale.ROOT).equals("me")) { //Проверка на использование me
							target = player;
						} else { //Ошибка, и всё к ней присущее
//							TextComponent errorCode = CommandError.UNKNOWN_PLAYER.errorCode();
//							Sound errorSound = CommandError.UNKNOWN_PLAYER.errorSound();

//							player.sendMessage(errorCode);
//							player.playSound(player.getLocation(), errorSound, 1, 2);

//							Dependencies.getLog().info("<=КОМАНДЫ AU=> Игрок " + player.getName() + " ввёл команду ,," + commandName + arguments + "'' и вызвал ошибку:");
//							Dependencies.getLog().info("<=КОМАНДЫ AU=> " + ChatColor.RED + expection.getMessage());
//							Dependencies.getLog().info("<=КОМАНДЫ AU=> Игроку " + player.getName() + " было отправленно сообщение о ошибке: " + legacyAmpersand().serialize(errorCode));

							player.sendMessage("я выебал кастомные ошибки");

							return true;
						}
					}
					Integer[] array0 = {0}; //Обозначает, что в строке nextArgument будет пропущен аргумент arg[0]
					String nextArguments = ml.mops.utils.Utils.combineStrings(args, " ", array0);

					if (nextArguments.isEmpty() || nextArguments.isBlank() || nextArguments == null) { //Если последующих аргументов нет
						String rank = ChatColor.GRAY + ""; //стандартный ранг
						String name = target.getName();
//						Dependencies.putMopsRank(target, rank);
//						Dependencies.putMopsName(target, name);
//						Dependencies.getLog().info("<=РАНГИ=> Записано имя " + name + "с рангом " + rank); //логги

						player.sendMessage("АХАХАХАХ МОПС РАНК");

						ml.mops.utils.Utils.updateDisplayName(target); //Обновление имени
						sender.sendMessage("временно не работает");

						if (target.equals(player)) { //Сообщения о успешном ресете ранга
							target.sendMessage(ChatColor.GREEN + "Вы ресетнули свой ранг.");
						} else {
							final TextComponent c1 = Component.text("Вы ресетнули ранг игроку ").color(NamedTextColor.GREEN).append(target.name().color(NamedTextColor.GREEN));
							player.sendMessage(c1);
							final TextComponent c2 = Component.empty().append(player.name().color(NamedTextColor.GREEN)).append(Component.text(" ресетнул Ваш ранг."));
							target.sendMessage(c2);

							player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);
							target.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);
						}

						player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);
						return true;
					}

					String rank = nextArguments; //Установка ранга
//					Dependencies.putMopsRank(target, rank);

					ml.mops.utils.Utils.updateDisplayName(target); //Обновление имени

					if (target.equals(player)) { //Сообщение о успешном выполнении команды
						target.sendMessage(ChatColor.GREEN + "Вы изменили свой ранг.");
					} else {
						final TextComponent c1 = Component.text("Вы изменили ранг игроку ").color(NamedTextColor.GREEN).append(target.name().color(NamedTextColor.GREEN));
						player.sendMessage(c1);
						final TextComponent c2 = Component.empty().append(player.name().color(NamedTextColor.GREEN)).append(Component.text(" изменил Ваш ранг."));
						target.sendMessage(c2);

						player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);
						target.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);
					}
					break;
				}



				case "test" -> {
					try {
						player.sendMessage("тут мнооого тестинга");
					} catch (ArrayIndexOutOfBoundsException event) {
						player.sendMessage("ало ты какой то там эррей не написал");
					}
					return true;
				}
				case "slimetest" -> {
					Slime slime = (Slime) player.getWorld().spawnEntity(player.getLocation(), EntityType.SLIME);

					int integer = 1;
					if (args.length == 0 || args[0].equals("")) {
						player.sendMessage(ChatColor.RED + "Напишите цифру.");
					} else {
						try {
							integer = Integer.parseInt(args[0]);
						} catch (Exception e) { player.sendMessage("я выебал кастомные ошибки"); }

						slime.setSize(integer);
					}
					return true;
				}
				case "vector" -> {
					double x = 0;
					double y = 0;
					double z = 0;
					try {
						x = Double.parseDouble(args[0]);
						y = Double.parseDouble(args[1]);
						z = Double.parseDouble(args[2]);
					} catch (ArrayIndexOutOfBoundsException ignored) {
						player.sendMessage("здрасте использование команды: /vector x y z ник_игрока");
					}

					try {
						player = Bukkit.getServer().getPlayer(args[3]);
					} catch (ArrayIndexOutOfBoundsException exception) {
						player = (Player) sender;
					}

					assert player != null;
					player.setVelocity(new Vector(x, y, z).multiply(2));
					return true;
				}
				case "kickall" -> {
					Map<String, String> legacyCodeMap = new HashMap<String, String>();
					legacyCodeMap.put("&s", " ");
					String inputString = ml.mops.utils.Utils.combineStrings(args, CHARACTER.SPACE);
					String string = "<error>";
					try {
						string = ml.mops.utils.Utils.legacyAmpersandStringToDeprecatedBukkitChatColor(inputString.trim(), legacyCodeMap, MAP_BOOLEAN_MODE.UNION);
					} catch (Exception e) {
						plugin.getLogger().warning("<AU> " + e.getMessage());
					}

					for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
						if (!player1.isOp()) {
							player1.kickPlayer(string);
						} else {
							player1.sendMessage(ChatColor.RED + "Возможно вам стоить выйти по причине " + ChatColor.RESET + string);
						}
					}
					return true;
				}
				case "loreadd" -> {
					Map<String, String> legacyCodeMap = new HashMap<String, String>();
					legacyCodeMap.put("&s", " ");
					String inputString = ml.mops.utils.Utils.combineStrings(args, CHARACTER.SPACE);
					String string = "<error>";
					try {
						string = ml.mops.utils.Utils.legacyAmpersandStringToDeprecatedBukkitChatColor(inputString.trim(), legacyCodeMap, MAP_BOOLEAN_MODE.UNION);
					} catch (Exception e) {
						plugin.getLogger().warning("<AU> " + e.getMessage());
					}

					try {
						ItemStack item = player.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();

						List<String> lore = new ArrayList<>();

						assert meta != null;
						if(meta.hasLore()) lore = meta.getLore();

						assert lore != null;
						lore.add(string);

						meta.setLore(lore);

						item.setItemMeta(meta);
						player.sendMessage(ChatColor.GREEN + "Вы добавили " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + string + ChatColor.RESET + ChatColor.GREEN + " в описание предмета.");
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
					} catch (NullPointerException event) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "loreclear" -> {
					try {
						ItemStack item = player.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();
						assert meta != null;
						if (meta.hasLore()) {
							meta.setLore(new ArrayList<>());
							item.setItemMeta(meta);
							player.sendMessage(ChatColor.GREEN + "Вы очистили лор предмета.");
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
						} else {
							player.sendMessage(ChatColor.RED + "У предмета нет лора!");
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
						}
					} catch (NullPointerException event) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "loreremove" -> {
					try {
						try {
							ItemStack item = player.getInventory().getItemInMainHand();
							ItemMeta meta = item.getItemMeta();
							assert meta != null;
							if (meta.hasLore()) {
								List<String> lore = meta.getLore();
								assert lore != null;
								lore.remove(args[0]);

								meta.setLore(lore);
								item.setItemMeta(meta);
							} else {
								player.sendMessage(ChatColor.RED + "У предмета нет лора!");
								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
							}
						} catch (IndexOutOfBoundsException exception) {
							player.sendMessage(ChatColor.RED + "У предмета нет такой строчки!");
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
						}
					} catch (NullPointerException exception) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "nameitem" -> {
					Map<String, String> legacyCodeMap = new HashMap<String, String>();
					legacyCodeMap.put("&s", " ");
					String inputString = ml.mops.utils.Utils.combineStrings(args, CHARACTER.SPACE);
					String string = "<error>";
					try {
						string = ml.mops.utils.Utils.legacyAmpersandStringToDeprecatedBukkitChatColor(inputString.trim(), legacyCodeMap, MAP_BOOLEAN_MODE.UNION);
					} catch (Exception e) {
						plugin.getLogger().warning("<AU> " + e.getMessage());
					}

					try {
						ItemStack item = player.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();
						assert meta != null;
						meta.setDisplayName(string);
						item.setItemMeta(meta);
						player.sendMessage(ChatColor.GREEN + "Вы назвали предмет " + ChatColor.RESET + string + ChatColor.GREEN + ".");
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
					} catch (NullPointerException event) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "enchantitem" -> {
					try {
						String ench = args[0];
						String rawLevel = args[1];
						try {
							int lvl = Integer.parseInt(rawLevel);
							try {
								try {
									ItemStack item = player.getInventory().getItemInMainHand();
									ItemMeta meta = item.getItemMeta();
									assert meta != null;
									meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(ench))), lvl, true);
									item.setItemMeta(meta);
									player.sendMessage(ChatColor.GREEN + "Вы дали предмету зачарование " + ChatColor.RESET + ench + " " + lvl + ChatColor.GREEN + " уровня.");
									player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
								} catch (NullPointerException event) {
									player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
									player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
								}
							} catch (IllegalArgumentException event) {
								player.sendMessage(ChatColor.RED + "Это не найдено в базе зачарований.");
								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
							}
						} catch (NumberFormatException event) {
							player.sendMessage(ChatColor.RED + "Комманду нужно использовать как: /enchantitem " + ChatColor.AQUA + rawLevel + ench);
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
						}
					} catch (ArrayIndexOutOfBoundsException event) {
						player.sendMessage(ChatColor.RED + "Комманду нужно использовать как: /enchantitem " + ChatColor.AQUA + "<ЗАЧАРОВАНИЕ> " + "<УРОЕНЬ>");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "enchantclear" -> {
					try {
						ItemStack item = player.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();
						assert meta != null;
						if (meta.getEnchants().isEmpty()) {
							player.sendMessage(ChatColor.RED + "На предмете нет зачарований.");
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
						} else {
							item.getEnchantments().keySet().clear();
							player.sendMessage(ChatColor.GREEN + "Вы стёрли зачарования предмета.");
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
						}
					} catch (NullPointerException event) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "unbreak" -> {
					try {
						ItemStack item = player.getInventory().getItemInMainHand();
						ItemMeta meta = item.getItemMeta();
						assert meta != null;
						if (meta.isUnbreakable()) {
							player.sendMessage(ChatColor.YELLOW + "Вы " + ChatColor.RED + ChatColor.BOLD + "ВЫКЛЮЧИЛИ" + ChatColor.RESET + ChatColor.YELLOW + " неломаемость");
							meta.setUnbreakable(false);
						} else {
							player.sendMessage(ChatColor.YELLOW + "Вы " + ChatColor.GREEN + ChatColor.BOLD + "ВКЛЮЧИЛИ" + ChatColor.RESET + ChatColor.YELLOW + " неломаемость");
							meta.setUnbreakable(true);
						}
						item.setItemMeta(meta);
					} catch (NullPointerException event) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "color" -> {
					try {
						try {
							ItemStack item = player.getInventory().getItemInMainHand();
							ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
							LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
							String rgbBase16 = args[0].trim().replace("#", "");
							int rgbBase10 = Integer.parseInt(rgbBase16, 16);
							plugin.getLogger().info("ARGUMENT #0:       " + args[0]);
							plugin.getLogger().info("RGB BASE 16 (HEX): " + rgbBase16);
							plugin.getLogger().info("RGB BASE 10:       " + rgbBase10);
							leatherArmorMeta.setColor(Color.fromRGB(rgbBase10));

							item.setItemMeta(leatherArmorMeta);
						} catch (ClassCastException exception) {
							player.sendMessage(ChatColor.RED + "Предмет в вашей руке не кожанный!");
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
						}
					} catch (NullPointerException exception) {
						player.sendMessage(ChatColor.RED + "Вы не имеете предмета в руке!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "fly" -> {
					if (player.getAllowFlight()) {
						player.setAllowFlight(false);
						player.sendMessage(ChatColor.YELLOW + "Вы " + ChatColor.RED + ChatColor.BOLD + "ВЫКЛЮЧИЛИ" + ChatColor.RESET + ChatColor.YELLOW + " флай");
					} else {
						player.setAllowFlight(true);
						player.sendMessage(ChatColor.YELLOW + "Вы " + ChatColor.GREEN + ChatColor.BOLD + "ВКЛЮЧИЛИ" + ChatColor.RESET + ChatColor.YELLOW + " флай");
					}
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
					return true;
				}
				case "food" -> {
					try {
						int inputFoodLevel = Integer.parseInt(args[0]);
						player.setFoodLevel(inputFoodLevel);
						player.sendMessage(ChatColor.GREEN + "Вы установили себе " + inputFoodLevel + " еды.");
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Вы написали не цифру!");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					}
					return true;
				}
				case "announce" -> {
					if (args.length == 0 || args[0].equals("")) {
						player.sendMessage(ChatColor.RED + "Вам нужно написать хоть что то.");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
					} else {
						Map<String, String> legacyCodeMap = new HashMap<String, String>();
						legacyCodeMap.put("&s", " ");
						String inputString = ml.mops.utils.Utils.combineStrings(args, CHARACTER.SPACE);
						String string = "<error>";
						try {
							string = ml.mops.utils.Utils.legacyAmpersandStringToDeprecatedBukkitChatColor(inputString.trim(), legacyCodeMap, MAP_BOOLEAN_MODE.UNION);
						} catch (Exception e) {
							plugin.getLogger().warning("<AU> " + e.getMessage());
						}

						for (Player player1 : Bukkit.getServer().getOnlinePlayers()) {
							player1.sendMessage(string);
							player1.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
						}
					}
					return true;
				}

				default -> { //Сообщение о том что комманда не найдена
//					TextComponent errorCode = CommandError.ARGUMENT_NAN.errorCode();
//					Sound errorSound = CommandError.ARGUMENT_NAN.errorSound();

//					player.sendMessage(errorCode);
//					player.playSound(player.getLocation(), errorSound, 1, 2);

//					Dependencies.getLog().info("<=КОМАНДЫ AU=> Игрок " + player.getName() + " ввёл неизвестную команду ,," + commandName + arguments + "''");
//					Dependencies.getLog().info("<=КОМАНДЫ AU=> Игроку " + player.getName() + " было отправленно сообщение о ошибке: " + legacyAmpersand().serialize(errorCode));
					player.sendMessage("я выебал кастомные ошибки");
				}
			}
//		} else {
//			TextComponent errorCode = CommandError.NO_OP.errorCode(); //Логи
//			Sound errorSound = CommandError.NO_OP.errorSound();
//
//			sender.sendMessage(errorCode);
//
//			Dependencies.getLog().info("<=КОМАНДЫ AU=> Существо/Игрок С/БЕЗ оп " + sender.getName() + " ввёл(о) команду ,," + commandName + arguments + "''");
//			Dependencies.getLog().info("<=КОМАНДЫ AU=> Существу " + sender.getName() + " было отправленно сообщение о ошибке: " + legacyAmpersand().serialize(errorCode));
//		}
//		if (sender instanceof Player) {
//			Player player = (Player) sender;
//			if (commandName.equals("customgive")) {
//				if (perms) {
//					player.openInventory(Dependencies.getCustomGive());
//				} else {
//					player.sendMessage(ChatColor.RED + "У вас нет OP!");
//					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
//				}
//				return true;
//			}


			if (commandName.equals("name")) {
				if (args.length == 0 || args[0].equals("")) {
//						Dependencies.putMopsName(player, player.getName());
					player.sendMessage(ChatColor.GREEN + "Вы ресетнули свой ник.");
					player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);

					player.sendMessage("не робе");
				} else {
					String string = args[0];
					String string1 = string.replace("_", " ").replace("&0", ChatColor.BLACK + "").replace("&1", ChatColor.DARK_BLUE + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&c", ChatColor.RED + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&e", ChatColor.YELLOW + "").replace("&f", ChatColor.WHITE + "").replace("&k", ChatColor.MAGIC + "").replace("&l", ChatColor.BOLD + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&r", ChatColor.RESET + "");
//						Dependencies.putMopsName(player, string1.substring(0, 16));
					player.sendMessage(ChatColor.GREEN + "Вы изменили свой ник на " + ChatColor.RESET + string1);

					player.sendMessage("не робе");
				}
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
//					player.setPlayerListName(Dependencies.getMopsRank(player) + Dependencies.getMopsName(player));
				return true;
			}
			if (commandName.equals("rank")) {
				if (args.length == 0 || args[0].equals("")) {
//						Dependencies.putMopsRank(player, ChatColor.GRAY + "");
					player.sendMessage(ChatColor.GREEN + "Вы ресетнули свой ранг.");
					player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 2);

					player.sendMessage("не робе");
				} else {
					String string = args[0];
					String string1 = string.replace("_", " ").replace("&0", ChatColor.BLACK + "").replace("&1", ChatColor.DARK_BLUE + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&c", ChatColor.RED + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&e", ChatColor.YELLOW + "").replace("&f", ChatColor.WHITE + "").replace("&k", ChatColor.MAGIC + "").replace("&l", ChatColor.BOLD + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&r", ChatColor.RESET + "");
//						Dependencies.putMopsRank(player, string1.substring(0, 16) + " ");
					player.sendMessage(ChatColor.GREEN + "Вы изменили свой ранг на " + ChatColor.RESET + string1);

					player.sendMessage("не робе");
				}
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
//					player.setPlayerListName(Dependencies.getMopsRank(player) + Dependencies.getMopsName());

				player.sendMessage("не робе");
				return true;
			}














			return false;
		} else if (sender instanceof Player player) {
			player.sendMessage(ChatColor.RED + "У вас нет OP!");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1, 2);
		}
		return false;
	}
}
