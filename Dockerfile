FROM clojure

ENV APPPATH /srv/app/

RUN mkdir -p $APPPATH
WORKDIR $APPPATH

COPY project.clj $APPPATH
RUN lein deps
COPY . $APPPATH

RUN mv "$(lein ring uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar

CMD ["java", "-jar", "app-standalone.jar"]
