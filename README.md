这是一个rcon通讯协议的实现
其中有block和async（多路复用实现）版本，前者用于单个客户端，后者可用在web服务器上给多个客户端提供服务（使用时推荐利用websocket来传输数据）
在reactorReceive与testReceive中有更好的使用样例
