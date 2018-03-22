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

		if (!(args.isEmpty())) {
			System.out.println(String.format("%s: %s", e.getAuthor().getName(), " " + args));
			e.getChannel().sendMessage(String.format("%s: %s", e.getAuthor().getAsMention(), " " + args)).queue();
		}

		// gives information
		if (command.equalsIgnoreCase("?") || command.equalsIgnoreCase("help")) {
			information(e);
		}

		if (command.equalsIgnoreCase("Decide")) {
			if (args.isEmpty()) {
				e.getChannel().sendMessage("Theres only one choice.......You need help I can't provide!!!!").queue();
				e.getChannel().sendMessage("" + e.getAuthor().getAsMention() + " check my profile pic for the correct choice :wink:").queue();
			} else {
				msg = randDec(args, msg);
				e.getChannel().sendMessage(msg).queue();
			}
		}

	}

	// output for help
	private void information(MessageReceivedEvent e) {
		e.getChannel().sendMessage(String.format(
				"The available list of commands are: \n\nGeneral Commands\n\nHelp:\nThese massages\n\nDecide: \ndecide is used to help make a decision.\n "
						+ "enter in single words with a space or use commas (,) to give the list of options to select from."))
				.queue();
	}

	private StringBuilder randDec(String args, StringBuilder msg) {
		Random r = new Random();
		msg.append("The decision is: ");
		String[] decisions;
		String decided = "";
		int rand;
		if (!(args.contains(","))) {
			decisions = args.split(" ");
		} else {
			decisions = args.split(",");
		}
		rand = (int) Math.floor(r.nextDouble() * 100);
		System.out.println("Random number is: " + rand);
		if (rand > 0) {
			decided = decisions[rand%decisions.length].trim();
		} else {
			decided = decisions[0].trim();
		}
		msg.append(decided);
		return msg;
	}

}

// new TimerTriggers().onTimerTriggers(eventMills, adds);
// membs = e.getGuild().getDefaultChannel().getMembers();