FROM clojure

ENV APPPATH /srv/app/

RUN mkdir $APPPATH
WORKDIR $APPPATH

ADD project.clj $APPPATH
RUN lein deps

ADD . $APPPATH

CMD ["lein", "ring", "server-headless"]
