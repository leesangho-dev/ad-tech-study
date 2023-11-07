use admin

db.createUser({
    user: "studyuser",
    pwd: "1234",
    roles: [
        { role: "readWrite", db: "study" }
    ]
})
