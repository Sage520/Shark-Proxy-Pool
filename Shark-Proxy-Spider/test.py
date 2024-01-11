from mq.basic import ProxyAddTo
from mq.producer import send_to_add_queue

if __name__ == '__main__':
    to = ProxyAddTo()
    to.ip = '1.1.1.1'
    to.port = 80
    to.source = 'test'
    to.type = 1

    send_to_add_queue(to)
