
  Feature: MailChimp
    Scenario Outline: Sign up
      Given i test in "<browser>"
      And i set "<mail>"
      And also set <username>
      And set "<password>"
      When i press the button
      Then i get "<result>" and "<expect>" this outcome

      Examples:
        | browser | mail        | username | password | result    | expect                                                                            |
        | chrome  | cat@cat.com | 100      | fW2@mmmm | fail      | Enter a value less than 100 characters long                                       |
        | edge    | cat@cat.com | 0        | fW2@mmmm | fail      | Great minds think alike - someone already has this username. If it's you, log in. |
        | edge    | cat         | 4        | fW2@mmmm | fail      | An email address must contain a single @.                                         |
        | chrome  | cat@cat.com | 5        | fW2@mmmm | successes | Check your email                                                                  |