import base64
import logging
import sys
from typing import List
from argparse import ArgumentParser, Namespace

import requests

logger = logging.getLogger(__name__)


def configure_logging(verbosity: bool) -> None:
   level = logging.DEBUG if verbosity else logging.WARNING
   logging.basicConfig(
       level=level,
       format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
   )
   logging.getLogger('urllib3').setLevel(logging.CRITICAL)


def parse_args(args: List) -> Namespace:
   parser = ArgumentParser(description="Our first program")
   parser.add_argument('-u', '--username', type=str, required=True)
   parser.add_argument('-p', '--password', type=str, required=True)
   parser.add_argument('-v', '--verbosity', action='store_true', help='Enable debug logging')

   return parser.parse_args(args)


def get_auth_header(username: str, password: str) -> str:
   encoded_auth_string = f"{username}:{password}".encode('ascii')
   b64_auth_string = base64.b64encode(encoded_auth_string)
   auth_header = f"Basic {b64_auth_string.decode('ascii')}"
   logger.debug('Generated auth header for user %s', username)
   return auth_header


def get_httpbin_data(username: str, password: str) -> dict:
   endpoint = f"https://httpbin.org/basic-auth/{username}/{password}"
   headers = {'Accept': 'application/json', 'Authorization': get_auth_header(username, password)}
   logger.info('Requesting HTTPBin endpoint %s', endpoint)
   response = requests.get(endpoint, headers=headers)
   logger.debug('HTTP response received: %s', response.status_code)

   if response.ok:
       logger.info('Authentication succeeded for user %s', username)
       return response.json()

   logger.warning('Authentication failed for user %s with status %s', username, response.status_code)
   raise RuntimeError("Unable to get response from server")


def main(args: List):
   parsed_args = parse_args(args)
   configure_logging(parsed_args.verbosity)
   logger.debug('Program started with args: %s', parsed_args)
   logger.warning('This is a sample warning trace')
   response = get_httpbin_data(parsed_args.username, parsed_args.password)
   logger.debug('Response payload: %s', response)
   print(response)


if __name__ == '__main__':
   main(sys.argv[1:])