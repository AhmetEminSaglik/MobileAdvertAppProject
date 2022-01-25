const db = require("../models");
const User = db.user;
const Op = db.Sequelize.Op;

class UserController {
    // Create and Save a new User
    create = (req, res) => {

        console.log("veri geldi")
        // Save User in the database
        User.create(req.body)
            .then(data => {
                console.log(`Veri ekleme basarili`);
                res.send(data);
            })
            .catch(err => {

                console.log(err.errors[0].message);
                res.set('hata', err.errors[0].message).status(500).send({
                    message:
                        err.errors[0].message || "Some error occurred while creating the User."
                });
            });
    };
    login = (req, res) => {
        const username = req.body.username;
        const password = req.body.password; 

        User.findAll({
            where: {
                username: username,
                password: password
            }
        })
            .then(data => {
                res.send(data);
            })
            .catch(err => {
                res.status(500).send({
                    message:
                        err.message || "Some error occurred while retrieving tutorials."
                });
            });
    };

    // Retrieve all Tutorials from the database.
    findAll = (req, res) => {
        // const title = req.query.title;
        // var condition = title ? { title: { [Op.like]: `%${title}%` } } : null;

        // User.findAll({ where: condition })
        User.findAll()
            .then(data => {
                res.send(data);
            })
            .catch(err => {
                res.status(500).send({
                    message:
                        err.message || "Some error occurred while retrieving tutorials."
                });
            });
    };

    // Retrieve all Tutorials from the database.
    // findUserByIdA = (req, res) => {
    //     const  userId = req.params.id;
    //     console.log(userId);
    //     var condition = title ? { title: { [Op.like]: `%${title}%` } } : null;

    //     User.findAll({ where: condition })
    //         .then(data => {
    //             res.send(data);
    //         })
    //         .catch(err => {
    //             res.status(500).send({
    //                 message:
    //                     err.message || "Some error occurred while retrieving tutorials."
    //             });
    //         });
    // };

    // Find a single User with an id
    login = (req, res) => {
        const username = req.body.username;
        const password = req.body.password; 
        
        User.findAll({
            where: {
                username: username,
                password: password
            }
        })
            .then(data => {
                res.send(data);
            })
            .catch(err => {
                res.status(500).send({
                    message:
                        err.message || "Some error occurred while retrieving tutorials."
                });
            });
    };
    findUserById = (req, res) => {
        const id = req.params.id;

        User.findByPk(id)
            .then(data => {
                if (data) {
                    res.send(data);
                } else {
                    res.status(404).send({
                        message: `Cannot find User with id=${id}.`
                    });
                }
            })
            .catch(err => {
                res.status(500).send({
                    message: "Error retrieving User with id=" + id
                });
            });
    };

    // Update a User by the id in the request
    update = (req, res) => {
        const id = req.params.id;

        User.update(req.body, {
            where: { id: id }
        })
            .then(num => {
                if (num == 1) {
                    res.send({
                        message: "User was updated successfully."
                    });
                } else {
                    res.send({
                        message: `Cannot update User with id=${id}. Maybe User was not found or req.body is empty!`
                    });
                }
            })
            .catch(err => {
                res.status(500).send({
                    message: "Error updating User with id=" + id
                });
            });
    };

    // Delete a User with the specified id in the request
    delete = (req, res) => {
        const id = req.params.id;

        User.destroy({
            where: { id: id }
        })
            .then(num => {
                if (num == 1) {
                    res.send({
                        message: "User was deleted successfully!"
                    });
                } else {
                    res.send({
                        message: `Cannot delete User with id=${id}. Maybe User was not found!`
                    });
                }
            })
            .catch(err => {
                res.status(500).send({
                    message: "Could not delete User with id=" + id
                });
            });
    };

    // Delete all Tutorials from the database.
    deleteAll = (req, res) => {
        User.destroy({
            where: {},
            truncate: false
        })
            .then(nums => {
                res.send({ message: `${nums} Tutorials were deleted successfully!` });
            })
            .catch(err => {
                res.status(500).send({
                    message:
                        err.message || "Some error occurred while removing all tutorials."
                });
            });
    };

    // find all published User
    findAllPublished = (req, res) => {
        User.findAll({ where: { isBanned: 0 } })
            .then(data => {
                res.send(data);
            })
            .catch(err => {
                res.status(500).send({
                    message:
                        err.message || "Some error occurred while retrieving tutorials."
                });
            });
    };


}

module.exports = new UserController()