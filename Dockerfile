FROM clojure

RUN mkdir /app
WORKDIR /app
ADD project.clj /app/
RUN lein deps

ADD . /app/

