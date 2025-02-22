# Q&A

## git 占用最大的5个文件
~~~ shell
git rev-list --objects --all | grep "$(git verify-pack -v .git/objects/pack/*.idx | sort -k 3 -n | tail -10 | awk '{print$1}')"
~~~

## terminal 走代理

~~~ shell
export ALL_PROXY=http://127.0.0.1:49595

~~~

## docker

### dify 

docker compose up -d

## power

powercfg /requests
