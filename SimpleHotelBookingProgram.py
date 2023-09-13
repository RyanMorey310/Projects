POOL_PASS = 150
KIDS_CLUB_PRICE = 100


def review_booking(acom_list, acom_cost_list, acom_amount_list, extra_type_list, extra_amount_list):
    print(f'\nOVERALL DETAILS')
    print(f'=' * 16)
    print(f'{acom_list[0]}: {acom_amount_list[0]} booked.')
    print(f'{acom_list[1]}: {acom_amount_list[1]} booked.')
    print(f'{acom_list[2]}: {acom_amount_list[2]} booked.')
    print(f'{extra_type_list[0]}: {extra_amount_list[0]} kid(s) attending.')
    print(f'{extra_type_list[1]}: {extra_amount_list[1]} bought.')

    #Average Income per Booking
    dc_income = acom_amount_list[0] * acom_cost_list[0]
    st_income = acom_amount_list[1] * acom_cost_list[1]
    cs_income = acom_amount_list[2] * acom_cost_list[2]
    kc_income = extra_amount_list[0] * KIDS_CLUB_PRICE
    pp_income = extra_amount_list[1] * POOL_PASS
    sum_bookings = sum(acom_amount_list)
    total_income = dc_income + st_income + cs_income + kc_income + pp_income

    if sum(acom_amount_list) > 0:
        average = total_income / sum_bookings
        print(f'Average Income Per Booking: ${average:.2f}')
    else:
        print(f'Average Income Per Booking: $0.00')

    #Total Income
    print(f'Total Projected Income: ${total_income:.2f}')

    #Sites Remaining
    print(f'Sites Remaining: {30 - sum_bookings}')

    #Most popular site
    if sum_bookings >= 5:
        if acom_amount_list[0] > acom_amount_list[1] and acom_amount_list[0] > acom_amount_list[2]:
            print(f'Most Popular Site: {acom_list[0]}\n')
        elif acom_amount_list[1] > acom_amount_list[0] and acom_amount_list[1] > acom_amount_list[2]:
            print(f'Most Popular Site: {acom_list[1]}\n')
        elif acom_amount_list[2] > acom_amount_list[1] and acom_amount_list[2] > acom_amount_list[0]:
            print(f'Most Popular Site: {acom_list[2]}\n')
        else:
            print('There is no most popular site.\n')
    else:
        print('Not enough booking to determine most popular.\n')


def booking(acom_list, acom_cost_list, acom_amount_list, extra_type_list, extra_amount_list):
    # Reading in the surname
    surname = ''
    while len(surname) <= 0 or len(surname) > 14 or not all((x.isalpha() or x == "'" for x in surname)):
        surname = input('Enter the surname being used: ')
        if len(surname) <= 0 or len(surname) > 14 or not all((x.isalpha() or x == "'" for x in surname)):
            print('Please enter a valid surname of less than 14 length (apostrophes and letters only).\n')

    # Reading in the contact number
    contact_no = ''
    while len(contact_no) > 12 or len(contact_no) <= 0 or not all(x.isnumeric() for x in contact_no):
        contact_no = input('Enter your contact number: ')
        if len(contact_no) > 12 or not all(x.isnumeric() for x in contact_no):
            print('Please enter a valid number of 12 digits or less.\n')

    # Reading in the accommodation type
    print(f'\n1. {acom_list[0]} ({acom_cost_list[0]}), {acom_amount_list[0]} booked.')
    print(f'2. {acom_list[1]} ({acom_cost_list[1]}), {acom_amount_list[1]} booked.')
    print(f'3. {acom_list[2]} ({acom_cost_list[2]}), {acom_amount_list[2]} booked.')
    accommodation = -1
    while accommodation < 0 or accommodation > 3:
        try:
            accommodation = int(input('Choose your accommodation from the menu above: '))
        except ValueError:
            print('Please enter a valid input (1-3).')

    # Reading in the amount of people
    people = -1
    while people < 0:
        try:
            people = int(input('Enter the amount of people in your group: '))
        except ValueError:
            print('Please enter a whole number.\n')

    # Reading in the pool pass choice
    pool_pass = 'T'
    while pool_pass != 'y' and pool_pass != 'n':
        pool_pass = input('Will be you purchasing a family pool pass? (y/n) ').lower()
        if pool_pass != 'y' and pool_pass != 'n':
            print('Please enter y or n.\n')

    # Reading in the amount of kids club attendants
    kids_club = -1
    while kids_club < 0 or kids_club > people:
        try:
            kids_club = int(input('Enter the amount of kids attending the kids club: '))
        except ValueError:
            print('Please enter a positive whole number that is less than the total amount of people.\n')

    # Getting correct info for details and adding values to the lists
    if accommodation == 1:
        accommodation_cost = acom_cost_list[0]
        accommodation_type = acom_list[0]
        acom_amount_list[0] += 1
    elif accommodation == 2:
        accommodation_cost = acom_cost_list[1]
        accommodation_type = acom_list[1]
        acom_amount_list[1] += 1
    else:
        accommodation_cost = acom_cost_list[2]
        accommodation_type = acom_list[2]
        acom_amount_list[2] += 1

    kids_club_cost = kids_club * KIDS_CLUB_PRICE
    extra_amount_list[0] += kids_club

    if pool_pass == 'y':
        pool_cost = POOL_PASS
        pool_choice = 'Yes'
        extra_amount_list[1] += 1
    else:
        pool_cost = 0
        pool_choice = 'No'

    # Calculating Total
    total = pool_cost + accommodation_cost + kids_club_cost

    # Getting booking ID
    booking_id = acom_amount_list[0] + acom_amount_list[1] + acom_amount_list[2]

    # Printing Booking Details
    print(f'\nBOOKING DETAILS')
    print(f'=' * 16)
    print(f'Booking Id: {booking_id}')
    print(f'Accommodation Type: {accommodation_type}')
    print(f'No. of People: {people}')
    print(f'Pool Pass: {pool_choice}')
    print(f'No. for Kids Club: {kids_club}')
    print(f'Cost: ${total}\n')

    # Writing details to surname file
    filename = surname + '_' + str(booking_id) + '.txt'
    open_file = open(filename, 'w')
    print(f'\nBOOKING DETAILS', file=open_file)
    print(f'=' * 16 , file=open_file)
    print(f'Booking Id: {booking_id}', file=open_file)
    print(f'Accommodation Type: {accommodation_type}', file=open_file)
    print(f'No. of People: {people}', file=open_file)
    print(f'{extra_type_list[1]}: {pool_choice}', file=open_file)
    print(f'No. of {extra_type_list[0]}: {kids_club}', file=open_file)
    print(f'Cost: ${total}\n', file=open_file)
    open_file.close()


def get_extras_info():
    extra_type = []
    extra_amount = []
    with open('extras.txt') as data:
        for line in data:
            line_list = line.strip().split(',')
            extra_type.append(line_list[0])
            extra_amount.append(int(line_list[1]))
    return extra_type, extra_amount


def get_booking_info():
    accom_type = []
    cost = []
    amount = []
    with open('Booking_2022.txt') as data:
        for line in data:
            line_list = line.strip().split(',')
            accom_type.append(line_list[0])
            cost.append(int(line_list[1]))
            amount.append((int(line_list[2])))
    return accom_type, cost, amount

def write_booking_file(accom_list, cost_list, amount_list):
    open_file = open('Booking_2022.txt', 'w')
    for i in range(len(accom_list)):
        print(f'{accom_list[i]}, {cost_list[i]}, {amount_list[i]}', file=open_file)
    open_file.close()

def write_extra_file(extra_type, extra_amount):
    open_file = open('extras.txt', 'w')
    for i in range(len(extra_type)):
        print(f'{extra_type[i]}, {extra_amount[i]}', file=open_file)
    open_file.close()

def menu():
    print(f'LONG ISLAND HOLIDAYS')
    print('=' * 20)
    print(f'1. Make a Booking')
    print(f'2. Review Booking')
    print(f'3. Exit')
    choice = -1
    while choice <= 0 or choice > 3:
        try:
            choice = int(input('Enter the number that correspond to a choice above: '))
        except ValueError:
            print('Please enter a valid choice.\n')
    return choice

def main():
    print('Ryan Morey')
    accommodation, cost, amount = get_booking_info()
    extra_type, extra_amount = get_extras_info()

    choice = menu()
    while choice != 3:
        if choice == 1:
            if (amount[0] + amount[1] + amount[2]) < 30:
                booking(accommodation, cost, amount, extra_type, extra_amount)
            else:
                print(f'\nBookings are full.\n')

        if choice == 2:
            review_booking(accommodation, cost, amount, extra_type, extra_amount)
        choice = menu()

    write_booking_file(accommodation, cost, amount)
    write_extra_file(extra_type, extra_amount)
    print('Save Successful.')


main()

