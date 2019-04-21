import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.managers.GuildManager;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class EventBot extends ListenerAdapter {
    private String message;
    private String name;
    private String[] cenzo;
    private GuildController gController;
    private Guild guild;
    private int upomnienie = 0;
    private VoiceChannel channel;


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        //komendy = new String[]{"!banuj", "!play", "!role", "!komendy", "!kanal"};
        //cenzo=new CharSequence[] {"dupa","chuj","kurwa","jebać"};
        cenzo = new String[]{"dupa", "chuj", "kurwa", "jebać"};
        message = e.getMessage().getContentRaw();
        name = e.getMember().getUser().getName();

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


    private void showComends(MessageReceivedEvent e) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Bot_Java");
        builder.setTitle("Dostepne komendy:");
        builder.setColor(Color.BLUE);
        builder.addField("!komendy", "Wyswietla dostepne komendy", false);
        builder.addField("!kanal [nazwa_kategorii] [typ_kanalu(glosowy/tekstowy)] [nazwa_kanalu] opcjonalnie [delete]", "Tworzy/usuwa kanal w podanej kategorii o podanej nazwie  danego typu", false);
        builder.addField("!rola [nazwa_uzytkownika] [nazwa_roli]", "Nadaje podanemu uzytkownikowi wybrana role", false);
        builder.addField("!removerola [nazwa_uzytkownika] [nazwa_roli]", "Odbiera podanemu uzytkownikowi wybrana role", false);
        builder.addField("!rolacreate [nazwa_roli] ", "Tworzy nowa role z domyslnymi pozwoleniami",false);
        builder.addField("!movemember [nazwa_uzytkownika] [nazwa_kanalu_docelowego]","Przenosi wybraneko uzytkownika do podanego kanalu",false);
        e.getChannel().sendMessage(builder.build()).queue();
    }

    private void log(String log_message, MessageReceivedEvent e) {
        e.getGuild().getTextChannelById("564114733368606720").sendMessage(log_message).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

}

