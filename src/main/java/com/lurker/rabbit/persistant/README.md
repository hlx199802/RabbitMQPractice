## RabbitMQ消息持久化和Spring AMQP持久化的实现
### 三个条件
- 交换器持久化
- 队列持久化
- 消息持久化
### 条件实现方式
#### 原生方式实现
- 交换器持久化
```text
//参数1：exchange - 交换器名称
//参数2：type - 交换器类型
//参数3：durable - 是否持久化
channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
```
- 队列持久化
```text
//参数1：exchange - 交换器名称
//参数2：durable - 是否持久化
//参数3：exclusive - 是否仅创建者可以使用的私有队列，断开后自动删除
//参数4：autoDelete - 是否当所有消费者断开后自动删除队列
//参数5：arguments
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
```
- 消息持久化
```text
//参数1：exchange - 交换器
//参数2：routingKey - 路由键
//参数3：props - 消息参数，其中MessageProperties.PERSISENT_TEXT_PLAIN表示持久化
//参数4：body - 消息体
channel.basicPublish("", queueName, MessageProperties.PERSISENT_TEXT_PLAIN, message.getBytes());
```
#### Spring AMQP方式实现
Spring AMQP是对原生RabbitMQ客户端的封装，只需要定义交换器的持久化和队列的持久化就可以实现持久化了
- 交换器持久化
```text
//参数1：name - 交换器名称
//参数2：durable - 是否持久化
//参数3：autoDelete - 当所有消费端断开连接后，是否自动删除队列
new TopicExchange(name, durable, autoDelete);
```
- 队列持久化
```text
//参数1：name - 队列名称
//参数2：durable - 是否持久化
//参数3：exclusive - 仅创建者可以使用的私有队列，断开后自动删除
//参数4：autoDelete - 当所有消费者连接断开后，是否自动删除队列
new Queue(name, durable, exclusive, autoDelete);
```
### Spring AMQP中不用定义消息持久化的原因源码分析
- 消息发送
```text
rabbitTempate.convertAndSend(exchange, routeKey, message);
```
- convertAndSend(String exchange, String routingKey, final Object object);方法
```text
@Override
public void convertAndSend(String exchange, String routingKey, final Object object) throws AmqpException {
    convertAndSend(exchange, routingKey, object, null);
}
```
- convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData);方法
```text
public void convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData) {
    send(exchange, routingKey, convertMessageIfNecessary(object), correlationData);
}
```
- 关键方法convertMessageIfNecessary(final Object object);
```text
protected Message convertMessageIfNecessary(final Object object) {
    if(obj instanceOf Message) {
        return (Message) object;
    }
    return getRequiredMessageConverter().toMessage(object, new MessageProperties());
}
```
- 关键类MessageProperties.class
```text
public class MessageProperties implements Serializable {
    public MessageProperties() {
        this.deliveryMode = DEFAULT_DELIVERY_MODE;
        this.priority = DEFAULT_PRIORITY;
    }
    static {
        DEFAULT_DELIVERY_MODE = MessageDeliveryMode.PERSISTENT;
        DEFAULT_PRIORITY = Integer.valueOf(0);
    }
}
```