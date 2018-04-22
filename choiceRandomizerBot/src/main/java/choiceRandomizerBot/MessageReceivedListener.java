package choiceRandomizerBot;

import java.util.Random;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent e) {
		// check for prefix
		if (!e.getMessage().getContentRaw().startsWith(choiceRandomizerBot.prefix))
			return;
		// check channel type
		if (e.getChannelType() == ChannelType.PRIVATE) {
			System.out.println(String.format("[DM] %s#%s: %s", e.getAuthor().getName(),
					e.getAuthor().getDiscriminator(), e.getMessage().getContentRaw()));
		} else {
			System.out.println(String.format("[%s][%s] %s#%s: %s", e.getGuild().getName(), e.getChannel().getName(),
					e.getAuthor().getName(), e.getAuthor().getDiscriminator(), e.getMessage().getContentRaw()));
		}
		// shortcuts for common parts of message
		String command = e.getMessage().getContentStripped().replace(choiceRandomizerBot.prefix, "").split(" ")[0];
		String args = e.getMessage().getContentRaw().replace(choiceRandomizerBot.prefix, "").replace(command, "")
				.trim();
		StringBuilder msg = new StringBuilder();
		
		System.out.println(command);

		// if args not empty this will post the message sent along with an @ to the
		// individual
		/*
		 * if (!(args.isEmpty())) { System.out.println(String.format("%s: %s",
		 * e.getAuthor().getName(), " " + args));
		 * e.getChannel().sendMessage(String.format("%s: %s",
		 * e.getAuthor().getAsMention(), " " + args)).queue(); }
		 */

		// gives information
		if (command.equalsIgnoreCase("?") || command.equalsIgnoreCase("help")) {
			information(e);
		}

		if (command.equalsIgnoreCase("Decide")) {
			if (args.isEmpty()) {
				e.getChannel().sendMessage("Check my profile pic for the correct choice :wink:").queue();
			}
			if (!(args.contains(" ") || args.contains(",")) && !(args.isEmpty())) {
				e.getChannel().sendMessage("Theres only one choice.......You need help I can't provide!!!!").queue();
			} 
			if (!(args.isEmpty()) && (args.contains(" ") || args.contains(","))) {
				msg = randDec(args, msg);
				e.getChannel().sendMessage(msg).queue();
			}
		}
		
		if (command.equalsIgnoreCase("FNSOLO")) {
			args = "Junk Junction, Haunted Hills, Pleasant Park, Anarchy Acres, Wailing Woods, Tomato Town, Wailing Woods, "
					+ "Loot Lake, Snobby Shores, Tilted Towers, Dusty Depot, Retail Row, Lonley Lodge, Greasy Grove, Shifty Shafts, Salty Springs, "
					+ "Fatal Fields, Moisty Mire, Flush Factory, Cargo, Prison, Stadium, Mountain near Fatal, Mountain near Pleasant, Mountain near Snobby";
			msg = randDec(args, msg);
			e.getChannel().sendMessage(msg).queue();
		}
		
		if (command.equalsIgnoreCase("FNDUOS")) {
			args = "Junk Junction, Haunted Hills, Pleasant Park, Anarchy Acres, Wailing Woods, Tomato Town, Wailing Woods, "
					+ "Loot Lake, Snobby Shores, Tilted Towers, Dusty Depot, Retail Row, Lonley Lodge, Greasy Grove, Shifty Shafts, Salty Springs, "
					+ "Fatal Fields, Moisty Mire, Flush Factory, Cargo, Prison, Stadium, Mountain near Fatal";
			msg = randDec(args, msg);
			e.getChannel().sendMessage(msg).queue();
		}
		
		if (command.equalsIgnoreCase("FNSQUADS")) {
			args = "Junk Junction, Haunted Hills, Pleasant Park, Anarchy Acres, Wailing Woods, Tomato Town, Wailing Woods, "
					+ "Loot Lake, Snobby Shores, Tilted Towers, Dusty Depot, Retail Row, Lonley Lodge, Greasy Grove, Shifty Shafts, Salty Springs, "
					+ "Fatal Fields, Moisty Mire, Flush Factory, Cargo, Prison";
			msg = randDec(args, msg);
			e.getChannel().sendMessage(msg).queue();
		}
		
		if (command.equalsIgnoreCase("dice")) {
			String d1 = "", d2 = "";
			d1 = emojiSwitch(d1, 6);
			d2 = emojiSwitch(d2, 6);
			e.getChannel().sendMessage(d1 + d2).queue();
		}
		
		if (command.equalsIgnoreCase("d6")) {
			String d1 = "";
			d1 = emojiSwitch(d1, 6);
			e.getChannel().sendMessage(d1).queue();
		}
		
		if (command.equalsIgnoreCase("d4")) {
			String d1 = "";
			d1 = emojiSwitch(d1, 4);
			e.getChannel().sendMessage(d1).queue();
		}
		
		if (command.equalsIgnoreCase("d10")) {
			String d1 = "";
			d1 = emojiSwitch(d1, 10);
			e.getChannel().sendMessage(d1).queue();
		}
		
		if (command.equalsIgnoreCase("d12")) {
			String d1 = "";
			d1 = emojiSwitch(d1, 12);
			e.getChannel().sendMessage(d1).queue();
		}
		
		if (command.equalsIgnoreCase("d20")) {
			String d1 = "";
			d1 = emojiSwitch(d1, 20);
			e.getChannel().sendMessage(d1).queue();
		}
	}

	// output for help
	private void information(MessageReceivedEvent e) {
		e.getChannel().sendMessage(String.format(
				"The available list of commands are: \n\nGeneral Commands\n\nHelp:\nThese massages\n\nDecide: \ndecide is used to help make a "
						+ "decision. Any input can be used, however to make non text input work use the links to the file like example.jpeg or example.docx\n "
						+ "enter in single words with a space or use commas (,) to give the list of options to select from.\n\nFn*:\nfnsolo, fnduos, "
						+ "and fnsquads helps to decide where to drop on the Fortnite map"))
				.queue();
	}

	// randomizer using mod and a base of 100 if choices are less than 100
	private StringBuilder randDec(String args, StringBuilder msg) {
		Random r = new Random();
		msg.append("The decision is: ");
		String[] decisions;
		String decided;
		int rand;
		if (!(args.contains(","))) {
			decisions = args.split(" ");
		} else {
			decisions = args.split(",");
		}
		if (decisions.length < 100) {
			rand = (int) Math.floor(r.nextDouble() * 100);
		} else {
			rand = (int) Math.floor(r.nextDouble() * decisions.length);
		}
		System.out.println("Random number is: " + rand);
		if (rand > 0) {
			decided = decisions[rand % decisions.length].trim();
		} else {
			decided = decisions[0].trim();
		}
		msg.append(decided);
		return msg;
	}
	
	private int randDec(int i, int x) {
		Random r = new Random();
		int rand;
		rand = (int) Math.floor(r.nextDouble() * 100);
		System.out.println("Random number is: " + rand);
		System.out.println("mod is: " + rand % 6);
		if (rand > 0) {
			i = rand % x;
		} else {
			randDec(i, x);
		}
		return i;
	}
	
	private String emojiSwitch(String i, int b) {
		int a = 0;
		a = randDec(a, b);
		switch (a) {
		case 1: i = ":one:"; break;
		case 2: i = ":two:"; break;
		case 3: i = ":three:"; break;
		case 4: i = ":four:"; break;
		case 5: i = ":five:"; break;
		case 6: i = ":six:"; break;
		case 7: i = ":seven:"; break;
		case 8: i = ":eight:"; break;
		case 9: i = ":nine:"; break;
		case 10: i = ":one::zero:"; break;
		case 11: i = ":one::one:"; break;
		case 12: i = ":one::two:"; break;
		case 13: i = ":one::three:"; break;
		case 14: i = ":one::four:"; break;
		case 15: i = ":one::five:"; break;
		case 16: i = ":one::six:"; break;
		case 17: i = ":one::seven:"; break;
		case 18: i = ":one::eight:"; break;
		case 19: i = ":one::nine:"; break;
		case 20: i = ":two::zero:"; break;
		default: i = ":one:";
	}
		return i;
	}
}
