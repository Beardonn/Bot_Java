import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.util.*;
import java.time.Instant;
import java.util.List;

public class GuildEvent extends ListenerAdapter {
    private String[] message;
    private User nick;
    private Category category;
    private TextChannel deletext;
    private VoiceChannel deletevoice;
    private Channel channel;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent g){
        message=g.getMessage().getContentRaw().split(" "); //0 komenda 1 kategoria 2 typ 3 nazwa kanalu 4 usuniecie
        nick=g.getMessage().getAuthor(); //zganrnia nazwe wysylajacego wiadomosc
        category=g.getGuild().getCategories().stream().filter(p->p.getName().equalsIgnoreCase(message[1])).findAny().orElse(null); //sprawdza czy istnieje dana kategoria
        channel=g.getGuild().getChannels().stream().filter(c->c.getName().equalsIgnoreCase(message[3])).findAny().orElse(null); //sprawdza czy istnieje podany kanal

        if(message[0].equals("!kanal")){
            if (message.length<4 || category==null) //sprawdzamy czy dana kategoria w ogole istnieje
                Methods.log("Zla komenda, wpisz !komendy by zobaczyc",g);
            if (!message[2].equals("tekstowy") && !message[2].equals("glosowy") && !g.getMessage().getMember().getUser().isBot()) //sprawdzamy czy ktos sie przypadkiem nie pomylil przy wpisywaniu
                {
                Methods.log("Zla komenda, wpisz !komendy by zobaczyc",g);
                }

                if(message[2].equals("tekstowy")&&message.length<5) //jak damy tekstowy i nie ma delete
                {
                    createChanel(message,g);
                    Methods.log("Utworzona kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Utworzono");
                }else if(message[2].equals("glosowy")&& message.length<5) //to samo co u gory
                {
                    createChanel(message,g);
                    Methods.log("Utworzona kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Utworzono");
                } if(message[4].equals("delete"))  //przy usuwaniu
                {
                    if (channel==null) //brak kanalu
                    {
                        Methods.log("Nie ma takiego kanalu",g);
                    }else {
                    deleteChannel(message,g);
                    Methods.log("Usunieto kanal "+message[2]+" w kategori "+message[1]+" o nazwie "+message[3],g);
                    embedbuilder(g,message,nick,"Usunieto");

            }
                }
        }

        }
    private void createChanel(String[] args, GuildMessageReceivedEvent g){
        category=g.getGuild().getCategoriesByName(args[1],true).get(0);
        if (args[2].equals("tekstowy")){
        category.createTextChannel(args[3]).queue();
        }else if(args[2].equals("glosowy")){
        category.createVoiceChannel(args[3]).queue();

        }

    }
    private void deleteChannel(String[] args, GuildMessageReceivedEvent g){
        if (args[2].equals("tekstowy")){
        deletext = g.getGuild().getTextChannelsByName(args[3],true).get(0);
        deletext.delete().queue();
        }else if(args[2].equals("glosowy")){
            deletevoice = g.getGuild().getVoiceChannelsByName(args[3],true).get(0);
            deletevoice.delete().queue();
        }

    }
    /*public void log(String log_message,GuildMessageReceivedEvent g){
        g.getChannel().sendMessage(log_message).queue();

    }*/
    private void embedbuilder(GuildMessageReceivedEvent g, String[] args, User user, String operacja){
        EmbedBuilder builder=new EmbedBuilder();
        builder.setAuthor(user.getName());
        builder.setColor(Color.BLUE);
        builder.setTitle(operacja+" kanal");
        builder.setDescription(operacja+" kanal "+args[2]+" w kategori "+args[1]+" o nazwie "+args[3]);
        builder.setTimestamp(Instant.now());
        g.getGuild().getTextChannelById("564114733368606720").sendMessage(builder.build()).queue();
    }
}
