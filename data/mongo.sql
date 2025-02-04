/*
 Navicat Premium Data Transfer

 Source Server         : mongodb
 Source Server Type    : MongoDB
 Source Server Version : 70016
 Source Host           : localhost:27017
 Source Schema         : mental-heal

 Target Server Type    : MongoDB
 Target Server Version : 70016
 File Encoding         : 65001

 Date: 04/02/2025 17:56:24
*/


// ----------------------------
// Collection structure for articles
// ----------------------------
db.getCollection("articles").drop();
db.createCollection("articles");

// ----------------------------
// Documents of articles
// ----------------------------
db.getCollection("articles").insert([ {
    _id: ObjectId("678f9c090931b935cdb9e6cc"),
    title: "如何管理压力",
    content: "学习如何有效管理压力，保持心理健康。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/206"
    ],
    createTime: ISODate("2025-01-21T13:07:21.473Z"),
    updateTime: ISODate("2025-01-21T13:11:23.73Z"),
    isDeleted: false,
    category: "心理健康",
    _class: "com.dil.article.entity.Article",
    "review_status": "published"
} ]);
db.getCollection("articles").insert([ {
    _id: ObjectId("679c9b5fa4644d5cc4326d01"),
    title: "提升情绪智商",
    content: "了解情绪智商的重要性及提升方法。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/207"
    ],
    createTime: ISODate("2025-01-31T09:43:59.238Z"),
    updateTime: ISODate("2025-01-31T09:52:09.773Z"),
    isDeleted: false,
    category: "心理健康",
    "review_status": "published",
    _class: "com.dil.article.entity.Article"
} ]);
db.getCollection("articles").insert([ {
    _id: ObjectId("679ca0f7804c420b939f9942"),
    title: "建立健康的人际关系",
    content: "探索建立和维持健康人际关系的技巧。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/208"
    ],
    createTime: ISODate("2025-01-31T10:07:51.027Z"),
    updateTime: ISODate("2025-01-31T10:07:51.027Z"),
    isDeleted: false,
    category: "情感关系",
    "review_status": "published",
    _class: "com.dil.article.entity.Article"
} ]);
db.getCollection("articles").insert([ {
    _id: ObjectId("679ca12d804c420b939f9943"),
    title: "心理健康与饮食",
    content: "了解饮食对心理健康的影响及建议。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/209"
    ],
    createTime: ISODate("2025-01-31T10:08:45.157Z"),
    updateTime: ISODate("2025-01-31T10:08:45.157Z"),
    isDeleted: false,
    category: "心理健康",
    "review_status": "published",
    _class: "com.dil.article.entity.Article"
} ]);
db.getCollection("articles").insert([ {
    _id: ObjectId("679ca14e804c420b939f9944"),
    title: "正念冥想的好处",
    content: "探索正念冥想对心理健康的积极影响。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/2010"
    ],
    createTime: ISODate("2025-01-31T10:09:18.105Z"),
    updateTime: ISODate("2025-01-31T10:09:18.105Z"),
    isDeleted: false,
    category: "个人成长",
    "review_status": "published",
    _class: "com.dil.article.entity.Article"
} ]);
db.getCollection("articles").insert([ {
    _id: ObjectId("679ca16c804c420b939f9945"),
    title: "克服焦虑的小技巧",
    content: "学习一些简单有效的技巧来克服焦虑。",
    author: "zs",
    imageUrls: [
        "https://picsum.photos/400/2011"
    ],
    createTime: ISODate("2025-01-31T10:09:48.127Z"),
    updateTime: ISODate("2025-01-31T11:13:37.899Z"),
    isDeleted: false,
    category: "个人成长",
    "review_status": "published",
    _class: "com.dil.article.entity.Article"
} ]);

// ----------------------------
// Collection structure for assessment_results
// ----------------------------
db.getCollection("assessment_results").drop();
db.createCollection("assessment_results");

// ----------------------------
// Documents of assessment_results
// ----------------------------
db.getCollection("assessment_results").insert([ {
    _id: ObjectId("6790deefeb4948077a040c8e"),
    assessmentId: "6790dc9aeb4948077a040c8d",
    userId: NumberLong("1"),
    answers: [
        {
            questionIndex: NumberInt("0"),
            selectedOption: "A",
            score: NumberInt("1")
        }
    ],
    totalScore: NumberInt("1"),
    evaluation: "情绪状态良好",
    completedAt: ISODate("2025-01-22T12:05:03.557Z"),
    _class: "com.dil.evaluation.model.AssessmentResult"
} ]);

// ----------------------------
// Collection structure for assessments
// ----------------------------
db.getCollection("assessments").drop();
db.createCollection("assessments");

// ----------------------------
// Documents of assessments
// ----------------------------
db.getCollection("assessments").insert([ {
    _id: ObjectId("6790dc9aeb4948077a040c8d"),
    title: "情绪测评更新版",
    type: "EMOTION",
    description: "用于评估用户的情绪状态，更新版",
    estimatedTime: NumberInt("15"),
    questions: [
        {
            scoreRules: [
                {
                    optionKey: "A",
                    score: NumberInt("1")
                },
                {
                    optionKey: "B",
                    score: NumberInt("2")
                },
                {
                    optionKey: "C",
                    score: NumberInt("3")
                }
            ]
        }
    ],
    creatorId: NumberLong("1"),
    status: "approved",
    reviewComment: "通过审核",
    reviewerId: NumberLong("1"),
    createdAt: ISODate("2025-01-22T11:55:06.18Z"),
    updatedAt: ISODate("2025-01-22T11:59:46.456Z"),
    _class: "com.dil.evaluation.model.Assessment"
} ]);

// ----------------------------
// Collection structure for chat_messages
// ----------------------------
db.getCollection("chat_messages").drop();
db.createCollection("chat_messages");

// ----------------------------
// Documents of chat_messages
// ----------------------------
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a076a67ecc2d6fd6421d5b"),
    sender: "3",
    receiver: "2",
    content: "你好\n",
    timestamp: ISODate("2025-02-03T07:56:22.373Z"),
    roomId: "1",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a076b57ecc2d6fd6421d5c"),
    sender: "2",
    receiver: "3",
    content: "你好\n",
    timestamp: ISODate("2025-02-03T07:56:37.459Z"),
    roomId: "1",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a077c07ecc2d6fd6421d5d"),
    sender: "2",
    receiver: "3",
    content: "你有什么心理问题要咨询吗😊\n",
    timestamp: ISODate("2025-02-03T08:01:04.67Z"),
    roomId: "1",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a077d17ecc2d6fd6421d5e"),
    sender: "3",
    receiver: "2",
    content: "没有🤔\n",
    timestamp: ISODate("2025-02-03T08:01:21.835Z"),
    roomId: "1",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a1e073f5a42e4159376d99"),
    sender: "2",
    receiver: "3",
    content: "1\n",
    timestamp: ISODate("2025-02-04T09:40:03.323Z"),
    roomId: "2",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
db.getCollection("chat_messages").insert([ {
    _id: ObjectId("67a1e098f5a42e4159376d9a"),
    sender: "3",
    receiver: "2",
    content: "sd",
    timestamp: ISODate("2025-02-04T09:40:40.758Z"),
    roomId: "2",
    type: "PRIVATE",
    _class: "com.dil.chat.model.ChatMessage"
} ]);
