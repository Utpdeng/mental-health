# 接口设计

### **通用设计规范**

- **URL 格式**：`/resource/{id}/action`
- 请求方法：
  - `GET`：获取资源
  - `POST`：创建资源
  - `PUT/PATCH`：更新资源
  - `DELETE`：删除资源
- **响应格式**：JSON
- 状态码：
  - `200 OK`：成功
  - `201 Created`：资源创建成功
  - `400 Bad Request`：请求参数错误
  - `401 Unauthorized`：未授权
  - `403 Forbidden`：权限不足
  - `404 Not Found`：资源未找到
  - `500 Internal Server Error`：服务器错误

### **接口设计**

#### **1. 用户模块**

##### **1.1 注册用户**

- **URL**: `POST /api/users/register`

- 请求体:

  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "password": "secure_password"
  }
  ```

- 响应:

  ```json
  {
    "status":"200",
    "message":"注册成功",
    "data": {
      "userId": "123"
      "created_at": "2025-01-12T11:10:45.910+00:00",
      "updated_at": "2025-01-12T11:10:45.910+00:00",
    }
  }
  ```

##### **1.2 用户登录**

- **URL**: `POST /api/users/login`

- 请求体:

  ```json
  {
    "username": "admin",
    "password": "123456"
  }
  ```

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": {
          "token": "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoidXNlciIsInVzZXJfaWQiOjMsImV4cCI6MTczNzExODYzOSwidXNlcm5hbWUiOiJscyJ9.UXGhasO0Zj7X2IvHwLUziUKQ7yxFAfH2pIr6K4lEFHN0PQgPLXPj5fbY_NPMv6IEMs2sNaYqwvdOvAI_9-ITZw",
          "userId": 1,
          "role": "admin"
      }
  }
  ```


  {
      "status": "500",
      "message": "用户名或密码错误",
      "data": null
  }
  ```

##### **1.3 获取用户信息**

- **URL**: `GET /api/users/{user_id}`

- **Headers**: `Authorization: Bearer {token}`

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": {
          "userId": 1,
          "username": "admin",
          "email": "admin@123.com",
          "role": "admin",
          "avatarUrl": null,
          "createdAt": "2025-01-14T21:41:09",
          "nickname": "admin",
          "bio": null,
          "gender": "male",
          "birthdate": "2025-01-14",
          "location": "china",
          "preferences": "\"\"",
          "status": "active"
      }
  }
  ```

##### 1.4 获取用户列表

- URL：GET /api/users/list?role=user

```json
{
    "status": "200",
    "message": "操作成功",
    "data": [
        {
            "userId": 1,
            "username": "admin",
            "email": "admin@123.com",
            "role": "admin",
            "avatarUrl": null,
            "createdAt": "2025-01-14T21:41:09",
            "nickname": "admin",
            "bio": null,
            "gender": "male",
            "birthdate": "2025-01-14",
            "location": "china",
            "preferences": "\"\"",
            "status": "active"
        },
        {
            "userId": 2,
            "username": "zs",
            "email": "zs@123.com",
            "role": "doctor",
            "avatarUrl": null,
            "createdAt": "2025-01-14T21:42:01",
            "nickname": null,
            "bio": null,
            "gender": null,
            "birthdate": null,
            "location": null,
            "preferences": null,
            "status": "active"
        },
        {
            "userId": 3,
            "username": "ls",
            "email": "ls@123.com",
            "role": "user",
            "avatarUrl": null,
            "createdAt": "2025-01-14T21:42:35",
            "nickname": null,
            "bio": null,
            "gender": null,
            "birthdate": null,
            "location": null,
            "preferences": null,
            "status": "active"
        }
    ]
}
```



------



#### 2. 心理医生模块

##### **2.1 获取心理医生列表**

- **URL**: `GET /api/psychologists`

- 查询参数:

  - `specialization` (可选): 心理医生擅长领域
  - `page` (可选): 分页页码

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": [
          {
              "psychologistId": 1,
              "userId": 2,
              "username": "zs",
              "nickname": "张三",
              "avatarUrl": null,
              "title": "心理专家",
              "specialty": "猜",
              "introduction": null,
              "workYears": 1,
              "consultationFee": 1.00,
              "employmentStatus": "active",
              "gender": "male",
              "location": "china",
              "maxAppointmentsPerDay": 1000
          }
      ]
  }
  ```

##### **2.2 查看心理医生详情**

- **URL**: `GET /api/psychologists/{psychologist_id}`

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": {
          "psychologistId": 1,
          "userId": 2,
          "username": "zs",
          "nickname": "张三",
          "avatarUrl": null,
          "email": "zs@123.com",
          "phone": "13888888888",
          "title": "心理专家",
          "specialty": "猜",
          "introduction": null,
          "qualification": null,
          "workYears": 1,
          "consultationFee": 1.00,
          "employmentStatus": "active",
          "employmentDate": "2025-01-14",
          "resignationDate": "2025-01-14",
          "leaveStartDate": null,
          "leaveEndDate": null,
          "leaveReason": null,
          "maxAppointmentsPerDay": 1000,
          "gender": "male",
          "birthdate": "2025-01-16",
          "location": "china",
          "preferences": "王者荣耀",
          "createdAt": "2025-01-14T19:46:08",
          "updatedAt": "2025-01-14T19:46:11"
      }
  }
  ```

##### 2.3 编辑心理医生

**URL**: `POST/api/psychologists/updatePsychologist`

```json
{
    "status": "200",
    "message": "操作成功",
    "data": null
}
```



#### **3. 预约与咨询模块**

##### **3.1 创建预约**

- **URL**: `POST /api/appointments/create`

- 请求体:

  ```json
  {
      "userId": 3,
      "doctorId": 2,
      "scheduleTime": "2025-01-19 21:57:10",
      "form": "video",
      "content": "心理委屈",
      "time": "60"
  }
  ```

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": 2
  }
  ```

##### **3.2 查看预约详情**

- **URL**: `GET /api/appointments/{appointment_id}`

- **Headers**: `Authorization: Bearer {token}`

- 响应:

  ```json
  {
      "status": "200",
      "message": "操作成功",
      "data": {
          "id": 1,
          "userId": 3,
          "doctorId": 2,
          "doctorName": "zs",
          "doctorTitle": "心理专家",
          "userName": "ls",
          "userAvatar": null,
          "scheduleTime": "2025-01-18T21:57:10",
          "status": "pending",
          "paymentStatus": "unpaid",
          "form": "graphic",
          "content": "工作压力大，出现焦虑症状，影响睡眠",
          "time": "60",
          "createdAt": "2025-01-18T21:57:40",
          "updatedAt": "2025-01-18T22:48:06"
      }
  }
  ```

##### **3.3查询预约列表**

- URL：/api/appointments/list

```json
@RequestParam(required = false) Long userId,
@RequestParam(required = false) Long doctorId,
@RequestParam(required = false) String status
```



```json
{
    "status": "200",
    "message": "操作成功",
    "data": [
        {
            "id": 1,
            "userId": 3,
            "doctorId": 2,
            "doctorName": "zs",
            "doctorTitle": "心理专家",
            "userName": "ls",
            "userAvatar": null,
            "scheduleTime": "2025-01-18T21:57:10",
            "status": "pending",
            "paymentStatus": "unpaid",
            "form": "graphic",
            "content": null,
            "time": null,
            "createdAt": "2025-01-18T21:57:40",
            "updatedAt": "2025-01-18T21:57:42"
        }
    ]
}
```

##### 3.4 创建咨询记录

/api/consultations/create

```json
{
    "appointmentId": 2,
    "doctorId": 2,
    "userId": 3
}

{
    "status": "200",
    "message": "操作成功",
    "data": 2
}
```



##### 3.5 查询咨询记录列表

/api/consultations/list

```json
{
    "status": "200",
    "message": "操作成功",
    "data": [
        {
            "id": 2,
            "appointmentId": 2,
            "doctorId": 2,
            "userId": 3,
            "chatLog": null,
            "feedback": null,
            "createdAt": "2025-01-19T17:26:17",
            "updatedAt": "2025-01-19T17:26:17"
        },
        {
            "id": 1,
            "appointmentId": 1,
            "doctorId": 2,
            "userId": 3,
            "chatLog": null,
            "feedback": "\"\"",
            "createdAt": "2025-01-18T21:58:54",
            "updatedAt": "2025-01-18T21:58:56"
        }
    ]
}
```



##### 3.6 查询咨询记录详情

/api/consultations/1

```json
{
    "status": "200",
    "message": "操作成功",
    "data": {
        "id": 1,
        "appointmentId": 1,
        "doctorId": 2,
        "userId": 3,
        "chatLog": null,
        "feedback": "\"\"",
        "createdAt": "2025-01-18T21:58:54",
        "updatedAt": "2025-01-18T21:58:56"
    }
}
```

##### 3.7 更新咨询记录

/api/consultations/update

```json
{
    "id": 1,
    "feedback": "猜的真准"
}

{
    "status": "200",
    "message": "操作成功",
    "data": null
}
```

------

#### **4. 心理测评模块**

##### **4.1 获取测评列表**

- **URL**: `GET /api/assessments`

- 响应:

  ```json
  [
    {
      "assessment_id": 1,
      "name": "Anxiety Test",
      "description": "A test to measure anxiety levels",
      "question_count": 10
    }
  ]
  ```

##### **4.2 提交测评结果**

- **URL**: `POST /api/assessments/{assessment_id}/results`

- 请求体:

  ```json
  {
    "user_id": 12345,
    "answers": {
      "q1": "4",
      "q2": "2",
      "q3": "5"
    }
  }
  ```

- 响应:

  ```json
  {
    "result_id": 5678,
    "result_summary": "Moderate anxiety",
    "recommendation": "Consider consulting a psychologist."
  }
  ```

------

#### **5. 社区互动模块**

##### **5.1 创建帖子**

- **URL**: `POST /api/posts`

- 请求体:

  ```json
  {
    "user_id": 12345,
    "title": "Dealing with Anxiety",
    "content": "I want to share my experience...",
    "post_type": "discussion"
  }
  ```

- 响应:

  ```json
  {
    "message": "Post created successfully",
    "post_id": 2345
  }
  ```

##### **5.2 获取帖子列表**

- **URL**: `GET /api/posts`

- 查询参数:

  - `post_type` (可选): 过滤特定类型的帖子（如 `discussion`）。
  - `page` (可选): 分页页码。

- 响应:

  ```json
  [
    {
      "post_id": 2345,
      "title": "Dealing with Anxiety",
      "user_id": 12345,
      "created_at": "2025-01-10T12:00:00Z",
      "comment_count": 5
    }
  ]
  ```

------

#### **6. 管理模块**

##### **6.1 审核心理医生**

- **URL**: `PUT /api/admin/psychologists/{psychologist_id}/approve`

- **Headers**: `Authorization: Bearer {admin_token}`

- 响应:

  ```json
  {
    "message": "Psychologist approved successfully"
  }
  ```

##### **6.2 查看系统日志**

- **URL**: `GET /api/admin/logs`

- **Headers**: `Authorization: Bearer {admin_token}`

- 响应:

  ```json
  [
    {
      "log_id": 1,
      "user_id": 12345,
      "action": "User Login",
      "details": "{}",
      "created_at": "2025-01-10T10:00:00Z"
    }
  ]
  ```


#### 7. 文章管理

```json
### 接口请求示例

#### 1. 发布文章
**POST** `/api/articles`

**请求体:**
​```json
{
    "title": "如何保持心理健康",
    "content": "保持心理健康的方法有很多...",
    "author": "张三",
    "imageUrls": ["http://example.com/image1.jpg"],
    "category": "心理健康"
}
​```


#### 2. 更新文章
**PUT** `/api/articles/{id}`

**路径参数:**
- `id`: 文章ID (例如: `12345`)

**请求体:**
​```json
{
    "title": "更新后的心理健康指南",
    "content": "更新后的内容...",
    "author": "张三",
    "imageUrls": ["http://example.com/image2.jpg"],
    "category": "心理健康"
}
​```


#### 3. 删除文章
**DELETE** `/api/articles/{id}`

**路径参数:**
- `id`: 文章ID (例如: `12345`)

#### 4. 根据ID查询文章
**GET** `/api/articles/{id}`

**路径参数:**
- `id`: 文章ID (例如: `12345`)

#### 5. 查询所有文章
**GET** `/api/articles`

#### 6. 根据标题和分类查询文章
**GET** `/api/articles/search`

**查询参数:**
- `title`: 文章标题 (可选)
- `category`: 文章分类 (可选)

例如:
- `/api/articles/search?title=心理健康`
- `/api/articles/search?category=心理健康`
- `/api/articles/search?title=心理健康&category=心理健康`

#### 7. 分页查询文章
**GET** `/api/articles/page`

**查询参数:**
- `pageNo`: 页码 (默认值: 1)
- `pageSize`: 每页大小 (默认值: 10)

例如:
- `/api/articles/page?pageNo=1&pageSize=10`

### 关于传入 `article` 时是否需要指定 `id`

在发布文章（**POST** `/api/articles`）时，不需要指定 `id`，因为 `id` 通常由数据库自动生成。在更新文章（**PUT** `/api/articles/{id}`）时，需要在URL路径中指定 `id`，而请求体中的 `id` 可以省略，因为更新操作会根据路径中的 `id` 来定位要更新的文章。
```




