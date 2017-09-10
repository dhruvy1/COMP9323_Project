from django.test import TestCase
from .facebook import getFacebookEvents

# Create your tests here.

getFacebookEvents("ArcUNSW", 1)

# class FacebookTestCase(TestCase):
#     def Should_ReturnAllPageEvents_Given_PageName(self):
#         page_events = getFacebookEvents("")
#         self.assertNotEqual(page_events, None)
