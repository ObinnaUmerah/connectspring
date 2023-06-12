Feature: Rest API functionalites

  Scenario: User is able to register and login
    Given That a user is able to register
    When I login to my account
    Then JWT key is returned

    Scenario: User is able to all of the post's features
      Given There are users in the database
      When I search for user by id
      Then the user is displayed
      When I search for posts by user
      Then A list of posts is displayed
      When I add a post to my post list
      Then post is added
      When I update a post from my post list
      Then the post is updated
      When I delete a post from my post list
      Then the property is deleted
