import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildController;
import net.dv8tion.jda.api.managers.GuildManager;

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
        cenzo = new String[]{"dobanu1", "zleslowo", "blacklista", "cenzurownaie"};
        message = e.getMessage().getContentRaw();
        name = e.getMember().getUser().getName();

        if (message.equals("!komendy") && !e.getMessage().getMember().getUser().isBot()) {
            showComends(e);
        }
        for (int i = 0; i < cenzo.length; i++) {
            if (message.toLowerCase().contains(cenzo[i])) {
                e.getMessage().delete().queue();
                /*upomnienie++;*/
                log(e);

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
        //builder.addField("!rolacreate [nazwa_roli] ", "Tworzy nowa role z domyslnymi pozwoleniami",false);
        builder.addField("!movemember [nazwa_uzytkownika] [nazwa_kanalu_docelowego]","Przenosi wybraneko uzytkownika do podanego kanalu",false);
        e.getChannel().sendMessage(builder.build()).queue();
    }

    private void log(MessageReceivedEvent e) {
        e.getMessage().getChannel().sendMessage("Usunieto wiadomosc").complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

}

