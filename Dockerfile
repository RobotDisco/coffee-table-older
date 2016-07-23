FROM clojure:alpine

EXPOSE 3000 7888

RUN mkdir -p /srv/app
WORKDIR /srv/app

COPY project.clj /srv/app/
RUN lein deps

COPY . /srv/app

ENV APP_ENV "prod"
ENV DATOMIC_URL "datomic:free://datomic:4334/coffee-table"

RUN mv "$(lein with-profile prod uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" app-standalone.jar
CMD ["java", "-jar", "app-standalone.jar"]
