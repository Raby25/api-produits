package payetonkawa.api_produits.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.product.queue}")
    private String queueName;

    @Value("${rabbitmq.product.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.product.routing-key}")
    private String routingKey;

    // Déclare la queue durable
    @Bean
    public Queue productQueue() {
        return new Queue(queueName, true);
    }

    // Déclare l'exchange durable
    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    // Lie la queue à l'exchange via la routing key
    @Bean
    public Binding productBinding(Queue productQueue, DirectExchange productExchange) {
        return BindingBuilder.bind(productQueue).to(productExchange).with(routingKey);
    }

    // AmqpAdmin pour déclarer automatiquement queue/exchange/binding
    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // Déclare un MessageConverter JSON pour envoyer/recevoir des objets

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
