FROM clojure

ENV APPPATH /srv/app/

RUN mkdir $APPPATH
WORKDIR $APPPATH

ADD project.clj $APPPATH
RUN lein deps

ADD src $APPPATH
ADD resources $APPPATH

ENTRYPOINT ["lein"]
CMD ["ring", "server-headless"]
