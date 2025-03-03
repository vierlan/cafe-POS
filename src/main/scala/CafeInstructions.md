# Mini Project: Café

You have been employed as the developer for a local independent café to help them refine their current Point of Sale (POS) system.

Work on a branch. Do not merge to main, just open a PR and send the link to me. Your work will be reviewed on this PR, just as it will on client site.

## MVP

Your task is to build the backend logic for a café POS system. We'll build our application through test driven development to gain confidence it works correctly.

Remember some of the key lessons we've learnt about developing something:
- Plan first, do this in whatever way works best for you.
- Start small and aim for TDD. Write a test and then just enough code to make that test pass. Refactor after.
- Test frequently! Never move too far away from a working solution. Remember to test not just your happy paths, but also that your code can fail at the correct points. Have you considered all edge cases?
- Work on a branch and commit frequently to that branch, don’t push directly to main.
- Make sure your commit message is clear. Present tense. If you are needing to use ‘and’ in the message you probably should have committed sooner!
- Writing code is collaborative, even though these are solo projects you can still talk to your peers, discuss ideas.
- Have fun with it!

### Client Requirements

#### Menu:
- A standard list of menu items.
- Premium specials need to be able to be added/removed from the menu.
- Stock count of the menu items. Some cannot order an item if the count is zero.

#### Bill:
- Take a customer order and produce an itemised bill which includes an optional service charge.

#### Service Charge:
- When all purchased items are no service charge is applied.
- When purchased items include any cold food, apply a service charge of 10% to the total bill (rounded to 2 decimal places).
- When purchased items include any hot food apply a service charge of 20% to the total bill.
- If a premium special is purchased, a service charge of 25% is added to the total bill.
- Ability to add a custom additional service charge, either in addition to or instead of the optional automatically applied service charge.

---

## Extensions

### Extension 1 - Loyalty Scheme:
- A customer can have one of two types of loyalty card – they cannot have both. Either a drinks loyalty card or a discount loyalty card.
- A customer can apply for a loyalty card but are not eligible if they already have the other card or are under 18.


- **Loyalty card 1 - Drinks loyalty card:**
    - Every time they purchase a drink, they receive a stamp. On the 10th visit, their drink is free.
    - They can only gain a stamp once per day.


- **Loyalty card 2 - Discount loyalty card:**
    - Every time their bill comes to more than £20, they receive a star. Each star will generate a discount on the total bill (before service charge is applied) of 2%.
    - They can only gain a star once per day.
    - The maximum number of stars is 8, once 8 is reached they will not receive any more stars but the total % discount of 16% will be taken off each bill over £20 (not including service charge).
    - Premium menu items are excluded from this discount.
    - To qualify for this discount card, their total spend over a minimum of 5 purchases needs to be £150.
    - E.g. if a customer purchased 4 times, each a minimum of £20, on 4 different days, they will be entitled to a discount of 8% on every purchase (regardless of total price). They can redeem this discount with every purchase, multiple times per day. On their 5th purchase of minimum £20 (after discount scheme), they will receive their 8% discount and a star (taking them to 5).


### Extension 2 - Multi-Currency & Staff Discount
This is an airport café.

Some of the customers may want to pay in other, well known, currencies. Implement the logic to allow the customer to choose the currency they pay in and produce the bill accordingly.

Airport employees receive an additional 10% staff discount if they have worked for the company for 6 months or more. This works in addition to all other offers and is calculated before service charge.


### Extension 3 - Happy Hour
Implement happy hour functionality where drinks are half price between 6pm and 7pm.

*(HINT: Check out the `java.time` library).*

During happy hour, loyalty cards do not apply to discounted drink purchases but will still work with food items.

### Extension 4 - Bill Enhancements
Add additional details to the produced bill:
- Date of transaction
- Time of transaction
- Staff member who completed the transaction
- Transaction type (cash/card)

### Extension 5 - End of Day Report
The client requires a reliable close of day routine. The report should include:
- **Total sales**, categorised into:
    - Hot Food, Cold Food, Hot Drinks, Cold Drinks, Alcoholic Drinks, Sundries, Premium Meals.
- **Total revenue**, separated by payment type:
    - Card, Cash, Amex.
- **Discounts used**, including:
    - Loyalty card discounts and manager discounts (e.g. comping food due to complaints).
- **An automatically generated shopping list** for any items at stock level 0.
- **Total profit**, relevant overheads need to be considered here.

### Extension 6 - Play Framework Frontend
This backend is great, but our café staff will eventually need something they can use!

**NOTE:** Research will be required for this extension!
- Make a simple frontend using the Play Framework.
- Add routes and controllers where necessary.
- Add controllers where necessary. Spend time considering why controllers are important and how they should be structured.
- Test your endpoints in a browser or via Bruno (installed during laptop setup).
- **Update the `README.md`** with instructions on how to run the project and access the frontend.