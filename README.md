# Patpat Bot

My telegram bot proof of concept.

- Able to response to incoming text message.
- Able to response to incoming command.


## How It Works

Lorem ipsum

## Installation 

Build with Maven and deployed to AWS Lambda 

### Regular JVM runtime

```bash
mvn clean package
sh target/manage.sh [create|update|delete|invoke]
```

### Native runtime

```bash
mvn clean package -Pnative -Dquarkus.native.container-build=true
sh target/manage.sh native [create|update|delete|invoke]
```

### Hints & Tips

1. [Avoid using private members](https://quarkus.io/guides/cdi-reference#native-executables-and-private-members)

	- Use the default (package) access modifier instead
	- To prevent reflection that results in a bigger native executable file
	
2. Caption length in sendPhoto API 
	
	- Text of 1024 characters (including line breaks).
	- On the safe side, keep it 1000 at max.


## Technologies

Quarkus _1.13.7.Final_ **WON'T** works on GraalVM _21.1.0._ Downgrade to _21.0.0.2_ and it works fine. 

 - GraalVM (Java 11)
 - Quarkus IO
 - AWS Lambda
 - AWS ApiGateway (REST)
 - AWS CloudWatch 
 - AWS S3
 - Telegram

## AWS Settings

1. S3 Bucket 
	
	- Images to be sent out will be stored here. The S3 bucket URL will be sent instead.
	- Policy = [Granting read-only permission to an anonymous user](https://docs.aws.amazon.com/AmazonS3/latest/userguide/example-bucket-policies.html#example-bucket-policies-use-case-2)


## Refreneces

### Emoji List
- http://www.get-emoji.com/
- https://gist.github.com/oliveratgithub/0bf11a9aff0d6da7b46f1490f86a71eb/

### Regex To Handle (only) emoji
- https://stackoverflow.com/a/67730175
- https://github.com/twitter/twemoji

### Telegram
- https://core.telegram.org/bots/webhooks
- https://core.telegram.org/bots/api#setwebhook
- https://core.telegram.org/bots/api
- https://core.telegram.org/bots#global-commands
- https://core.telegram.org/bots#commands
- https://core.telegram.org/bots/faq#broadcasting-to-users

### Coding
- https://quarkus.io/guides/rest-client
- https://dzone.com/articles/using-optional-correctly-is-not-optional
- https://quarkus.io/guides/config-mappings
- https://quarkus.io/guides/config