{
  'target_defaults': {
    'cflags': [
      '-fPIC',
    ],
    'include_dirs': [
      './'
    ],
  },

  'targets': [
    {
      'target_name': 'hello',
      'type': 'executable',
      'sources': [
        "src/hello.cc"
      ]
    },
    {
      'target_name': 'greet',
      'type': 'shared_library',
      'sources': [
        "src/greet.cc"
      ]
    }

  ]
}
