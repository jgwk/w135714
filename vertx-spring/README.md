# Spring with Vert.x and MongoDB

- Spring 을 기반으로 Vert.x 와 MongoDB 를 실행시킵니다.
- 임베디드 MongoDB 가 포함되어, 초기 실행시간이 느립니다.

## 실행

### 글 목록 조회

```bash
curl localhost/api/post
```

### 글 조회

```bash
curl localhost/api/post/:postId
```

### 글 등록

```bash
curl --request POST localhost/api/post \
--data-raw '{ "title": "title test", "content": "content test" }'
```

### 글 수정

```bash
curl --request PUT localhost/api/post/:postId \
--data-raw '{ "title": "test title", "content": "test content" }'
```

### 글 삭제
```bash
curl --request DELETE localhost/api/post/:postId
```