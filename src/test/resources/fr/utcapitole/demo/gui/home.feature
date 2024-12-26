Feature: Home

  Background:
    Given the user logs in as 'john_doe' with password 'foo'

  Scenario: Users land on a home page showing all categories
    Given the categories are
      | spring |
      | java   |
      | tdd    |
    When the user navigates to '/'
    Then the page contains a link 'spring' to '/questions/category/spring'
    And the page contains a link 'java' to '/questions/category/java'
    And the page contains a link 'tdd' to '/questions/category/tdd'
    And the page contains a link 'Ask question' to '/questions/ask'
