Feature: Test EasyNotes App

  @smoke @GetAll
  Scenario: GET All Notes
    Given the request headers are valid
    When I GET all notes
    Then I verify the status code as 200
    Then I validate the body of the GET All response

  @smoke @post
  Scenario Outline: Create and GET Note
    Given the request headers are valid
    When I create a new note with <Title> and <Content>
    Then I verify the status code as 200
    And I validate the body of the newly created note response
    And I GET the newly created note
    Then I verify the status code as 200
    Then I validate the body of the GET response

    Examples:
      | Title        | Content        |
      | Random Title | Random Content |

  @smoke @put
  Scenario Outline: Create and Update Note
    Given the request headers are valid
    When I create a new note with <Title> and <Content>
    Then I verify the status code as 200
    And I validate the body of the newly created note response
    And I Update the note with <attribute> to <length>
    Then I verify the status code as 200
    And I validate the Notes for the updated <attribute>

    Examples:
      | Title        | Content        | attribute | length |
      | Random Title | Random Content | title     | 10     |
      | Random Title | Random Content | content   | 50     |

  @smoke @delete
  Scenario Outline: Create and Delete Note
    Given the request headers are valid
    When I create a new note with <Title> and <Content>
    And I verify the status code as 200
    Then I Delete the newly created note
    And I verify the status code as 200

    Examples:
      | Title        | Content        |
      | Random Title | Random Content |