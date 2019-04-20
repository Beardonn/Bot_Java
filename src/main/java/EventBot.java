import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.GuildManager;

import java.awt.*;

public class EventBot extends ListenerAdapter {
    private String message;
    private String name;
    private String[] komendy;
    //private CharSequence[] cenzo;
    private String[] cenzo;
    private GuildController g;
    private Guild guild;
    private int upomnienie = 0;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        komendy = new String[]{"!banuj", "!play", "!role", "!komendy", "!kanal"};
        //cenzo=new CharSequence[] {"dupa","chuj","kurwa","jebać"};
        cenzo = new String[]{"dupa", "chuj", "kurwa", "jebać"};
        message = e.getMessage().getContentRaw();
        name = e.getMember().getUser().getName();
        if (message.equals("chuj")) {
            e.getChannel().sendMessage("jebac " + name).queue();
        }
        if (message.equals("!komendy") && !e.getMessage().getMember().getUser().isBot()) {
            showComends(e);
        }
        for (int i = 0; i < cenzo.length; i++) {
            if (message.toLowerCase().contains(cenzo[i])) {
                e.getMessage().delete().queue();
                upomnienie++;
                log("Usunieto wiadomosc", e);

            }
        }

    }


    public void showComends(MessageReceivedEvent e) {
        /*e.getChannel().sendMessage("Dostępne komendy:").queue();
        for (int i=0;i<komendy.length;i++){
            e.getChannel().sendMessage(komendy[i]+"\n").queue();
            //zrob to w embeded*/
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Bot_Java");
        builder.setTitle("Dostepne komendy:");
        builder.setColor(Color.BLUE);
        builder.addField("!komendy", "Wyswietla dostepne komendy", false);
        builder.addField("!kanal [nazwa_kategorii] [typ_kanalu(glosowy/tekstowy)] [nazwa_kanalu] opcjonalnie [delete]", "Tworzy/usuwa kanal w podanej kategorii o podanej nazwie  danego typu", false);
        builder.addField("!rola [nazwa_uzytkownika] [nazwa_roli]", "Nadaje podanemu uzytkownikowi wybrana role", false);
        builder.addField("!removerola [nazwa_uzytkownika] [nazwa_roli]", "Odbiera podanemu uzytkownikowi wybrana role", false);
        e.getChannel().sendMessage(builder.build()).queue();
    }

    public void log(String log_message, MessageReceivedEvent e) {
        e.getGuild().getTextChannelById("564114733368606720").sendMessage(log_message).queue();
    }
}

